#!python3

import os
import time
import subprocess
import numpy as np
import pandas as pd
import shutil

from sklearn.tree import DecisionTreeRegressor
from sklearn.linear_model import ElasticNet


class Profiler:

    def __init__(self, configs, jar, monitoring_output, sampling_name, rep):
        self.project_root = os.getcwd()
        self.configs = configs
        self.sampling = sampling_name
        self.jar = jar
        self.repititions = int(rep)
        # print("rep:", self.repititions)
        # self.jar = 'catena-0.0.1-SNAPSHOT.jar'
        self.parameter_file = "parameter.txt"

        # compress those configurations, that do not fit in RAM -> manual step
        self.recompute_uncompressable = True

        self.monitoring_output_base = monitoring_output+self.jar[:-4]+"/"+sampling_name
        print("output experiment results to:", self.monitoring_output_base)
        # self.monitoring_output = "/home/max/PerformanceHotSpotDetection/monitoring/confidence/output/monitoring_results/"
        # self.monitoring_output = "/run/media/max/6650AF2E50AF0441/monitoring_results/"
        # self.monitoring_output = "/home/max/uni/monitoring_results/output_no_prof/"

        print("init profiler with " + str(len(self.configs)) + " configs")
        #print("cwd " + self.project_root)

    def append_config_parameter(self, p_array, c):
        return p_array+[str(el) for el in c]

    def cmd_blank(self, c):
        parameter_array = []
        parameter_array.append("java")
        parameter_array.append("-jar")
        parameter_array.append('jars/'+self.jar)

        return self.append_config_parameter(parameter_array, c)

    def cmd_jip(self, c):
        c_f_name = "_".join(c)
        output_folder = self.monitoring_output_base+"/jip/"+c_f_name+"/"
        if not os.path.exists(output_folder):
            os.makedirs(output_folder)

        # update profiler properties
        profile_prop_path = self.project_root+"/lib/jip_properties/adaptedprofile.properties"
        output=''
        with open(profile_prop_path, "r") as f:
            for line in f:
                if "file=" in line:
                    output += "file="+output_folder+"\n"
                else:
                    output += line

        prof_prop_adapted = profile_prop_path+"__"+c_f_name
        with open(prof_prop_adapted, "w") as text_file:
            text_file.write(output)

        javaagent = "-javaagent:lib/profile.jar"
        Dprofile  = "-Dprofile.properties="+prof_prop_adapted

        parameter_array_jip = []
        parameter_array_jip.append("java")
        parameter_array_jip.append(javaagent)
        parameter_array_jip.append("-noverify")
        parameter_array_jip.append(Dprofile)
        parameter_array_jip.append("-jar")
        parameter_array_jip.append('jars/'+self.jar)

        if self.jar == "sunflow.jar":
            c = c + [output_folder]

        return self.append_config_parameter(parameter_array_jip, c)

    def cmd_kieker(self, c):
        c_f_name = "_".join(c)
        output_folder_kieker = self.monitoring_output_base+"/kieker/"+c_f_name+"/"
        # print("create kieker job array - dir:", str(os.path.exists(output_folder_kieker)))
        if not os.path.exists(output_folder_kieker):
            os.makedirs(output_folder_kieker)
            print("create folder", output_folder_kieker)

        # update profiler properties
        profile_prop_path_kieker = self.project_root+"/build/META-INF/kieker.monitoring.properties"
        output=''
        identifier_str = 'kieker.monitoring.writer.filesystem.AsciiFileWriter.customStoragePath='
        with open(profile_prop_path_kieker, "r") as f:
            for line in f:
                if identifier_str in line:
                    output+=identifier_str+output_folder_kieker+"\n"
                    #print("found it")
                    # print(identifier_str, output_folder_kieker)
                else:
                    output += line

        prof_prop_adapted_kieker = profile_prop_path_kieker+"__"+c_f_name
        # print("properties file:", prof_prop_adapted_kieker)
        # print("aop.xml file:", self.project_root+"/build/META-INF/aop.xml")
        with open(prof_prop_adapted_kieker, "w") as text_file:
            text_file.write(output)

        kieker_str_arr = []
        kieker_str_arr.append("java")
        kieker_str_arr.append("-javaagent:lib/kieker-1.13-aspectj.jar")
        kieker_str_arr.append("-Dkieker.monitoring.skipDefaultAOPConfiguration=true")
        # kieker_str_arr.append("-Dkieker.monitoring.configuration=build/META-INF/kieker.monitoring.properties")
        kieker_str_arr.append("-Dkieker.monitoring.configuration=file:"+prof_prop_adapted_kieker)
        kieker_str_arr.append("-Dorg.aspectj.weaver.loadtime.configuration=file:"+self.project_root+
                              "/build/META-INF/aop.xml")
        kieker_str_arr.append("-jar")
        kieker_str_arr.append('jars/'+self.jar)

        return self.append_config_parameter(kieker_str_arr, c)

    def cmd_kieker_args(self, c):
        print("starting kieker with annotations")
        c_f_name = "_".join(c)
        output_folder_kieker = self.monitoring_output_base+"/kieker_args/"+c_f_name+"/"
        # print("create kieker job array - dir:", str(os.path.exists(output_folder_kieker)))
        if not os.path.exists(output_folder_kieker):
            os.makedirs(output_folder_kieker)
            print("create folder", output_folder_kieker)

        # update profiler properties
        profile_prop_path_kieker = self.project_root+"/build/META-INF-ARGS/kieker.monitoring.properties"
        output=''
        identifier_str = 'kieker.monitoring.writer.filesystem.FileWriter.customStoragePath='
        with open(profile_prop_path_kieker, "r") as f:
            for line in f:
                if identifier_str in line:
                    output+=identifier_str+output_folder_kieker+"\n"
                    #print("found it")
                    # print(identifier_str, output_folder_kieker)
                else:
                    output += line

        prof_prop_adapted_kieker = profile_prop_path_kieker+"__"+c_f_name
        # print("properties file:", prof_prop_adapted_kieker)
        # print("aop.xml file:", self.project_root+"/build/META-INF/aop.xml")
        with open(prof_prop_adapted_kieker, "w") as text_file:
            text_file.write(output)

        kieker_str_arr = []
        kieker_str_arr.append("java")
        kieker_str_arr.append("-javaagent:lib/kieker-1.14-SNAPSHOT-aspectj.jar")
        kieker_str_arr.append("-Dkieker.monitoring.skipDefaultAOPConfiguration=true")
        # kieker_str_arr.append("-Dkieker.monitoring.configuration=build/META-INF/kieker.monitoring.properties")
        kieker_str_arr.append("-Dkieker.monitoring.configuration=file:"+prof_prop_adapted_kieker)
        kieker_str_arr.append("-Dorg.aspectj.weaver.loadtime.configuration=file:"+self.project_root+
                              "/build/META-INF-ARGS/aop.xml")
        kieker_str_arr.append("-jar")
        kieker_str_arr.append('jars/'+self.jar)



        if self.jar == "sunflow_annotated.jar":
            c = c + [output_folder_kieker]
        #print('comand:', c)
        return self.append_config_parameter(kieker_str_arr, c)

    def join_config_python(self, c):
        out = "_".join(c)
        out = out.replace(" ", "")
        out = out.replace("/", "-")
        out = out.replace("=", "-")
        out = out.replace(";", "-")
        out = out.replace("'", "-")
        return out

    def cmd_py(self, c):
        # example call:
        # - output: /home/max/PhD/SubjectSystems/Python/faker/monitoring/test
        # - cfg: -r=5 -s=';' -l de_DE profile birthdate,ssn,sex
        # - python3 -m cProfile -o output -m faker cfg
        print("starting cProfile profiling all methods")
        c_f_name = self.join_config_python(c)
        num_files = 0

        output_folder_cProfile = self.monitoring_output_base+"/cProfile/"+c_f_name+"/"
        if not os.path.exists(output_folder_cProfile):
            os.makedirs(output_folder_cProfile)
            print("create folder", output_folder_cProfile)
        else:
            num_files = len(os.listdir(output_folder_cProfile))

        arr = []
        arr.append("python3")
        arr.append("-m")
        arr.append("cProfile")
        arr.append("-o")
        arr.append(output_folder_cProfile+str(num_files)+'.out')
        arr.append("-m")
        arr.append("faker")

        return self.append_config_parameter(arr, c)

    def start_monitoring(self, prof_arr, name, c):

        c_f_name = self.join_config_python(c)
        output_folder = self.monitoring_output_base+"/"+name+"_bb/"+c_f_name+"/"
        if not os.path.exists(output_folder):
            os.makedirs(output_folder)

        for run_num in range(self.repititions):

            stdout = open(output_folder+"stdout.txt", "ab")
            stderr = open(output_folder+"stderr.txt", "ab")
            starttime = time.time()

            subprocess.call(prof_arr, stdout=stdout, stderr=stderr)

            endtime = time.time()
            time.sleep(1)
            # print(str(run_num)+ " " + str(endtime-starttime))

            if name == 'kieker' or name == 'kieker_args':
                # due to the immens size of the profiling output the results need to be post-processed
                intermediate_path = self.monitoring_output_base + "/" + name + "/" + c_f_name + "/"
                print(intermediate_path)
                folder_to_compress = [os.path.join(intermediate_path, name) for name in os.listdir(intermediate_path) if os.path.isdir(os.path.join(intermediate_path, name))]
                print(folder_to_compress)
                if len(folder_to_compress) == 0:
                    print('No folder to be compressed.')
                elif len(folder_to_compress) > 1:
                    print('Too many folders to compress.')
                else:
                    self.results_to_pkl(folder_to_compress[0])
                    shutil.rmtree(folder_to_compress[0], ignore_errors=True)

            with open(output_folder+"log_bb_"+str(run_num)+".txt", "a") as logfile:
                logfile.write(name + str(run_num) + " " + str(endtime-starttime)+"\n")

    def results_to_pkl(self, path):
        file_list = os.listdir(path)

        dicts_per_file = [self.parse_monitoring_res_folder(os.path.join(path, n)) for n in file_list if '.map'not in n]

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
            # print(k, len(v), np.mean(v), np.std(v))
            # print(k, len(v))

            el_net_err = 0.0
            el_net_params = {}
            el_net_dump = None
            dec_tree_err = 0.0
            dec_tree_params = {}
            dec_tree_dump = None

            args, v = zip(*values)

            if len(args) > 0 and len(args[0]) > 0 and self.recompute_uncompressable:
                # use args as x and v(performance) as y to learn method execution time variance model
                el_net_err, el_net_params, el_net_dump = Profiler.model_el_net(args, v)
                dec_tree_err, dec_tree_params, dec_tree_dump = Profiler.model_dec_tree(args, v)

            # decison tree dump too big to serialize it
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
                        args = Profiler.reconstruct_datatypes(splitted_line[1:-1])
                else:
                    method_signature = line_parts[2]

                if method_signature not in methods:
                    methods[method_signature] = [(args, performance)]
                else:
                    methods[method_signature].append((args, performance))
        return methods

    @staticmethod
    def model_el_net(args, y):
        alpha = 0.1
        l1_ratio = 0.7
        enet = ElasticNet(alpha=alpha, l1_ratio=l1_ratio)
        enet.fit(args, y)
        res = enet.score(args, y)
        params = enet.get_params()
        coefs = []
        coefs.append(enet.intercept_)
        coefs = coefs + list(enet.coef_)
        return res, params, coefs

    @staticmethod
    def model_dec_tree(args, y):
        alpha = 0.1
        l1_ratio = 0.7
        dect = DecisionTreeRegressor()
        dect.fit(args, y)
        res = dect.score(args, y)
        params = dect.get_params()
        dump = None#pickle.dumps(dect.tree_)
        return res, params, dump

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

    def profile(self):

        # with open("profiler_log.txt", "w") as logfile:
        # logfile.write("Logging of Profiler time info.\n")
        # logfile.write("Running " + str(len(self.configs)) + " configs.\n")
        print('start profiling ...')
        if self.jar == 'faker.tmp':
            for c in self.configs:
                python_execution_array = self.cmd_py(c)
                # print(python_execution_array)
                self.start_monitoring(python_execution_array, 'cProfile', c)
                print(python_execution_array)
        else:
            for c in self.configs:
                
                # with open("profiler_log.txt", "a") as logfile:
                    # logfile.write("config: " + str(c)+"\n")

                # output files
                # stdout = open(self."stdout.txt","ab")
                # stderr = open("stderr.txt","ab")

                # create parameter arays:
                # no_prof_arr = self.cmd_blank(c)
                # jip_arr = self.cmd_jip(c)
                # kieker_arr = self.cmd_kieker(c)

                # print()
                # self.start_monitoring(no_prof_arr, 'no', c)
                # self.start_monitoring(jip_arr, 'jip', c)
                # self.start_monitoring(kieker_arr, 'kieker', c)

                kieker_arg_an = self.cmd_kieker_args(c)
                self.start_monitoring(kieker_arg_an, 'kieker_args', c)
                print(kieker_arg_an)

                # for python
                self.cmd_py(c)

        print("Done: " + str(c))
