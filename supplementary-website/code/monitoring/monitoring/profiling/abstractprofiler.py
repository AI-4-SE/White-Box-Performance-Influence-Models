#!python3

import os
import time
import numpy as np
import pandas as pd
from datetime import datetime
import shutil
from abc import ABC, abstractmethod
from sklearn.tree import DecisionTreeRegressor
from sklearn.linear_model import ElasticNet


class AbstractProfiler(ABC):

    def __init__(self, executable, software_name, sampling_strategy, profiler_name, cfg):
        self.project_root = os.getcwd()
        self.base_output_path = '/media/hdd/max/monitoring_output'
        self.output_path = ''
        self.software_name = software_name
        self.sampling_strategy = sampling_strategy
        self.profiler_name = profiler_name
        self.cfg = cfg

        self.start_time = 0
        self.end_time = 0
        # is defined by the profiler
        self.executable = os.path.join('jars', executable)
        # DEBUG
        self.bb_times = []

        # compress those configurations, that do not fit in RAM -> manual step
        self.recompute_uncompressable = True

    @abstractmethod
    def create_instance(self):
        pass

    @abstractmethod
    def profile(self, iteration, stdout, stderr):
        pass

    def execute_profiler(self, rep):
        print('config:', self.cfg)
        joined_cfg = AbstractProfiler.join_config(self.cfg)
        self.output_path = os.path.join(self.base_output_path,
                                        self.software_name,
                                        self.sampling_strategy,
                                        self.profiler_name,
                                        joined_cfg)
        AbstractProfiler.create_dir(self.output_path)

        self.create_instance()
        print('output path:', self.output_path)

        # TODO: in case of sunflow: we need an extra path parameter
        if self.software_name == 'sunflow':
            self.cfg += [self.output_path]

        # TODO: in case of prevayler: the configuration is printed to a configuration file
        if self.software_name == 'prevayler':
            config_path = './softwareConfs/ScalabilityTest.properties'
            cfg_dict = self.cfg
            # TODO implement configuration file handling
            new_path = AbstractProfiler.create_properties_file_prevayler(cfg_dict, config_path, joined_cfg)
            print('prevayler config', joined_cfg, config_path)
            print('prevayler config exist:', os.path.exists(config_path))
            # new_path = config_path
            self.cfg = [new_path]
            print('remove cfg')

        for iteration in range(int(rep)):

            stdout = open(self.output_path + '/stdout.txt', 'ab')
            stderr = open(self.output_path + '/stderr.txt', 'ab')

            self.start_time = time.time()
            self.profile(iteration, stdout, stderr)
            self.end_time = time.time()

            self.bb_times.append(self.end_time - self.start_time)

            print('is kieker:', self.profiler_name, self.profiler_name == 'ProfilerKiekerArgs')
            if self.profiler_name == 'ProfilerKiekerArgs':
                # due to the immens size of the profiling output the results need to be post-processed
                intermediate_path = self.output_path

                print(intermediate_path)

                folder_to_compress = [os.path.join(intermediate_path, name) for name in os.listdir(intermediate_path) if
                                      os.path.isdir(os.path.join(intermediate_path, name))]

                print(folder_to_compress)

                if len(folder_to_compress) == 0:
                    print('No folder to be compressed.')
                elif len(folder_to_compress) > 1:
                    print('Too many folders to compress.')
                else:
                    self.results_to_pkl(folder_to_compress[0])
                    shutil.rmtree(folder_to_compress[0], ignore_errors=True)

            time.sleep(1)

        self.dump_bb_times()
        return self.bb_times

    def dump_bb_times(self):
        print('save output time to', self.output_path+'/bb_time.txt')
        with open(self.output_path+'/bb_time.txt', 'w') as f:
            for item in self.bb_times:
                f.write("%s\n" % item)

    def results_to_pkl(self, path):
        file_list = os.listdir(path)

        dicts_per_file = [self.parse_monitoring_res_folder(os.path.join(path, n)) for n in file_list if '.map' not in n]

        merged_dict = {}
        for d in dicts_per_file:
            for k, v in d.items():
                # print(len(v))
                if k in merged_dict:
                    merged_dict[k] = merged_dict[k] + v
                else:
                    merged_dict[k] = v

        out_dict = []
        for k, values in merged_dict.items():

            el_net_err = 0.0
            el_net_params = {}
            el_net_dump = None
            dec_tree_err = 0.0
            dec_tree_params = {}

            args, v = zip(*values)

            if len(args) > 0 and len(args[0]) > 0 and self.recompute_uncompressable:
                # use args as x and v(performance) as y to learn method execution time variance model
                el_net_err, el_net_params, el_net_dump = AbstractProfiler.model_el_net(args, v)
                dec_tree_err, dec_tree_params, dec_tree_dump = AbstractProfiler.model_dec_tree(args, v)

            # decision tree dump too big to serialize it
            dec_tree_dump = None

            if len(v) > 100:
                upper_bound = int(len(v) * 0.99)
            else:
                upper_bound = len(v)

            v_reduced = sorted(v)[:upper_bound]

            hist, bin_edges = np.histogram(v_reduced, bins=100)
            # print(hist)
            # print(bin_edges)
            out_dict.append(
                [k, len(v), np.mean(v), np.median(v), np.std(v), np.sum(v), len(v_reduced), np.mean(v_reduced),
                 np.median(v_reduced), np.std(v_reduced), np.sum(v_reduced), hist, bin_edges,
                 el_net_err, el_net_params, el_net_dump, dec_tree_err, dec_tree_params, dec_tree_dump])

        df = pd.DataFrame(data=out_dict, columns=['name', 'length_o', 'mean_o', 'median_o', 'std_o', 'sum_o',
                                                  'length_r', 'mean_r', 'median_r', 'std_r', 'sum_r', 'hist',
                                                  'bin_edges', 'arg_el_net_err', 'arg_el_net_params',
                                                  'arg_el_net_coefs', 'arg_dec_tree_err', 'arg_dec_tree_params',
                                                  'arg_dec_tree_tree_'])
        df.to_pickle(path + '.pkl')

    def parse_monitoring_res_folder(self, file):
        # print(file)
        methods = {}
        with open(file, "r") as f:
            for line in f:
                line_parts = line.split(';')

                if not line_parts[0] == '$1':
                    continue

                performance = int(line_parts[6]) - int(line_parts[5])
                args = []

                if '#' in line_parts[2]:
                    splitted_line = line_parts[2].split('#')
                    method_signature = splitted_line[0]
                    if self.recompute_uncompressable:
                        args = []
                    else:
                        args = AbstractProfiler.reconstruct_datatypes(splitted_line[1:-1])
                else:
                    method_signature = line_parts[2]

                if method_signature not in methods:
                    methods[method_signature] = [(args, performance)]
                else:
                    methods[method_signature].append((args, performance))
        return methods

    @staticmethod
    def join_config(c):
        # TODO the config joinery depends on the configuration output software system
        if type(c) == list:
            out = "_".join(c)
        elif type(c) == dict:
            out = "_".join(c.values())
        else:
            out = "_".join(c)
        out = out.replace(" ", "")
        out = out.replace("/", "-")
        out = out.replace("=", "-")
        out = out.replace(";", "-")
        out = out.replace("'", "-")

        if out.startswith('-'):
            out = 'cfg'+out

        return out

    @staticmethod
    def reconstruct_datatypes(args):
        args_out = []
        for arg in args:
            if arg.endswith('L'):
                args_out.append(int(arg[:-1]))
            elif arg.endswith('I'):
                args_out.append(int(arg[:-1]))
            else:
                args_out.append(0)
        return args_out

    @staticmethod
    def model_el_net(args, y):
        alpha = 0.1
        l1_ratio = 0.7
        enet = ElasticNet(alpha=alpha, l1_ratio=l1_ratio)
        enet.fit(args, y)
        res = enet.score(args, y)
        params = enet.get_params()
        coefs = [enet.intercept_]
        coefs = coefs + list(enet.coef_)
        return res, params, coefs

    @staticmethod
    def model_dec_tree(args, y):
        dect = DecisionTreeRegressor()
        dect.fit(args, y)
        res = dect.score(args, y)
        params = dect.get_params()
        dump = None
        return res, params, dump

    @staticmethod
    def append_config_parameter(cmd, c):
        return cmd+[str(el) for el in c]

    @staticmethod
    def create_dir(path):
        if not os.path.exists(path):
            os.makedirs(path)
            print("create folder", path)

    @staticmethod
    def create_properties_file_prevayler(cfg_dict, config_path, joined_cfg):
        properties_file_output_path = config_path+'__'+joined_cfg
        # update profiler properties
        output = ''

        # add one filename for transaction dumps
        cfg_dict['TransactionLogDirectory'] = 'TransactionTest__' + joined_cfg

        keys = cfg_dict.keys()

        with open(config_path, "r") as f:
            for line in f:
                cpd = False
                # compare each line in the cpnfig file against some key of the dict
                for key in keys:
                    if key in line:
                        output += key + ' = ' + cfg_dict[key] + "\n"
                        print("found it")
                        cpd = True
                if not cpd:
                    output += line

        with open(properties_file_output_path, "w") as text_file:
            text_file.write(output)

        return properties_file_output_path
