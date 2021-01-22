#!python3

# IMPORTS
import os
import socket
import math
import pandas as pd
import numpy as np
from string import Template

from joblib import Parallel, delayed
import multiprocessing

# Abolute paths
# define hosenames and path to measurement results according to your local envoronment

# print(socket.gethostname())
if socket.gethostname() == '[name]':
    base_path = '/run/media/[name]/6650AF2E50AF0441/measurement_results/'
    processed_data_root = '/run/media/[name]/6650AF2E50AF0441/experiment_data/'
elif socket.gethostname() == '[name]':
    base_path = '/home/[name]/raid/[name]/projects/green_configuration/' \
                'performance_hot_spot_detection/monitoring_results/'
    processed_data_root = '/home/[name]/raid/intelligent_software_systems/projects/green_configuration/' \
                          'performance_hot_spot_detection/experiment_data/'
elif socket.gethostname() == '[name]':
    base_path = '/home/[name]/projects/whiteboxPerformance/measurement_results/'
    processed_data_root = '/home/[name]/projects/whiteboxPerformance/experiment_data/'
    bash_template_string = '/home/[name]/jprofiler11/bin/jpexport ${input} HotSpots -format=csv ${output}'
else:
    base_path = ''
    print('cant find hostname - base path empty.')

catena_root = base_path + 'catena/'
sunflow_root = base_path + 'sunflow/'
h2_root = base_path + 'h2/'
prevayler_root = base_path + 'prevayler/'
pmd_root = base_path + 'pmd/'
density_root = base_path + 'density-converter/'
cpd_root = base_path + 'cpd/'

# HELPER Methods
methods_area_identifier = "| Most expensive methods (by net time)\n"


# HELPER Classes
class Experiment:
    def __init__(self, config):
        self.config = config
        self.profiles = []

    def add_profile(self, profile):
        self.profiles.append(profile)


class Profile:
    def __init__(self):
        self.exec_time = ""
        self.methods = {}

    def set_time(self, time):
        self.exec_time = time

    def add_methods(self, methods):
        self.methods = methods


def extract_function_time_tuples(file):
    file_dict = {}
    flag_data_area_reached = False
    continue_one_time = True
    for i, line in enumerate(file):
        # print(i,line)

        if flag_data_area_reached:
            if line == "\n":
                # first time jump over next few header lines
                # then each line contains data to be extract
                # skip rest of file if next empty line
                if continue_one_time:
                    next(enumerate(file), None)
                    next(enumerate(file), None)
                    next(enumerate(file), None)
                    next(enumerate(file), None)
                    continue_one_time = False
                    continue
                else:
                    break
            # extract content:
            if not continue_one_time:
                # print(line.strip())
                key, value = extract_content(line)
                if key not in file_dict:
                    file_dict[key] = [value]
                else:
                    file_dict[key].append(value)
                # if key=="main.java.components.hash.algorithms.Blake2b:rotr64":
                #    print(file_dict[key])
        elif methods_area_identifier in line:
            flag_data_area_reached = True

    # print(file_dict)
    return file_dict


def extract_content(line):
    tmp_arr = line.split()
    # returns dict entry with key = Location and a tuple (Count, Time)
    return tmp_arr[3], (int(tmp_arr[0]), float(tmp_arr[1]))


def merge_prob_dict(in_dict):
    tmp_dict = {}
    for key in in_dict:
        values = np.array(in_dict[key])
        # tmp_dict.update({key:[np.sum(values[:,0]),np.sum(values[:,1])]})
        value = (np.sum(values[:, 0]), np.sum(values[:, 1]))
        tmp_dict[key] = [value]
    return tmp_dict


def reorder(experiment_results):
    for cfg in experiment_results:
        for profile in cfg.profiles:
            tmp_dict = profile.methods
            # input vactor area for lin reg
            # print("Number different functions:\t" + str(len(tmp_dict)))
            num_fkts = 0
            for value in tmp_dict.values():
                num_fkts += len(value)
            # print("Number function-value pairs:\t" + str(num_fkts))

            tmp_prob_dict = {}
            tmp_unprob_dict = {}
            for key in tmp_dict:
                # problem part:
                if len(tmp_dict[key]) >= 2:
                    tmp_prob_dict.update({key: tmp_dict[key]})
                else:
                    tmp_unprob_dict.update({key: tmp_dict[key]})
            # print("OK: " + str(num_unprob_func) + " Prob: " + str(num_prob_func))

            reordered_dict = merge_prob_dict(tmp_prob_dict)

            tmp_unprob_dict.update(reordered_dict)
            profile.methods = tmp_unprob_dict
    return experiment_results


def read_repititions(c_folder):
    config = os.path.split(c_folder)[1]
    exp = Experiment(config)

    monitoring_files = []
    for f in os.listdir(c_folder):
        # rm .png files in case of sunflow rendering engine
        if not f.endswith('.png'):
            monitoring_files.append(os.path.join(c_folder, f))

    for f in monitoring_files:
        prof = Profile()
        time_start_id = f.rfind('/')
        exec_time = f[time_start_id + 1:-4]
        prof.set_time(exec_time)
        prof.add_methods(extract_function_time_tuples(open(f)))

        exp.add_profile(prof)
    return exp


def save_exp_to_pkl(sub_sys, sam_str, p_name, exps):
    # sub_sys, sam_str, prof, experiment
    print('num', len(exps))
    sub_sys_name = sub_sys[sub_sys[:-1].rfind('/') + 1:-1]
    out_name = sub_sys_name + '__' + sam_str + '__' + p_name + '.pkl'
    print('name of plk', out_name)

    columns = ['m_name', 'num', 'perf', 'sam_strat', 'profiler', 'config', 'rep']
    res = []

    # iterate over configurations
    for ex in exps:
        cfg = ex.config
        prof = ex.profiles

        # iterate over repititions
        for idx, rep in enumerate(prof):

            keys = rep.methods.keys()

            for key in keys:
                num, p = rep.methods[key][0]
                # print(sam_str)
                res.append([key, num, p, sam_str, p_name, cfg, idx])
                # print(key, num, p, sam_str, p_name, cfg, idx)
                # df.append(key, num, p, out_name[4:-4], p_name, cfg, idx)
    df = pd.DataFrame(res, columns=columns)

    df.to_pickle(processed_data_root + out_name)
    return df


def process_jip_measurements(prof_path):
    cfgs = os.listdir(prof_path)
    num_cfgs = len(cfgs)
    print('Total number cfgs:', num_cfgs)

    experiments = []
    for cfg in cfgs:
        cfg_path = os.path.join(prof_path, cfg)
        if not os.path.isdir(cfg_path):
            continue

        exp = read_repititions(cfg_path)
        experiments.append(exp)
        # break
    exps_reordered = reorder(experiments)
    return exps_reordered


def process_kieker_measurements(prof_path):
    exp_list = []
    cfgs = [os.path.join(prof_path, cfg) for cfg in os.listdir(prof_path)]

    for cfg in cfgs:
        curr_cfg = os.path.basename(os.path.normpath(cfg))
        config = read_cfg(cfg)
        if config is None:
            continue
        config['config'] = curr_cfg
        exp_list.append(config)

    experiment = pd.concat(exp_list)
    # dump: out = "/run/media/max/6650AF2E50AF0441/monitoring_results_alphaweb/experiment.pkl"
    return experiment


def read_cfg(cfg):
    repetitions = []
    repetition_name = [run for run in os.listdir(cfg) if run.endswith('.pkl')]

    if len(repetition_name) < 1:
        return None

    for i, rep in enumerate(repetition_name):
        df = pd.read_pickle(os.path.join(cfg, rep))
        save = [variance_of_hist(row['hist'], row['bin_edges']) for index, row in df[['hist', 'bin_edges']].iterrows()]
        df['var_hist'] = save
        df['rep'] = i
        repetitions.append(df)
    return pd.concat(repetitions)


def variance_of_hist(hist, bins):
    # Calc Mean = Sum(i=1 to N=#OfBins) i*hist(i)
    mean_val = 0

    width = (bins[1] - bins[0]) / 2
    centered_bins = [single_bin + width for single_bin in bins[:-1]]
    total_n = sum(hist)
    bins_n = len(bins) - 1

    weighted_bins = [h * b for h, b in list(zip(hist, centered_bins))]
    total_p = sum(centered_bins)
    mean_val = total_p / bins_n

    # Calc Variance = Sum(i=1 to N=#OfBins) (i-Mean)^2 * hist(i)
    variance_arr = [math.pow(b - mean_val, 2) * h for h, b in list(zip(hist, centered_bins))]
    return sum(variance_arr) / total_n


def save_exp_kieker_to_pkl(sub_sys, sam_str, p_name, exp):
    sub_sys_name = sub_sys[sub_sys[:-1].rfind('/') + 1:-1]
    out_name = sub_sys_name + '__' + sam_str + '__' + p_name + '.pkl'
    print('name of plk', out_name)
    exp.to_pickle(processed_data_root + out_name)
    return exp


# ---------------------------------------------------------------
# jProfiler
# ---------------------------------------------------------------

def process_jprofiler_measurements(path):
    df_list = []
    cfgs = os.listdir(path)
    total_cfgs = len(cfgs)
    print('Num configs to process:', total_cfgs)
    for cfg in cfgs:
        cfg_path = os.path.join(path, cfg)
        if not os.path.isdir(cfg_path):
            continue
        df = process_jprofiler_repetitions(cfg_path)
        if df is not None:
            df['config'] = cfg
            df_list.append(df)
    return pd.concat(df_list)


def process_jprofiler_repetitions(path):
    config = os.path.split(path)[1]
    # exp = Experiment(config)
    # print(config)

    monitoring_files = []
    for f in os.listdir(path):
        # rm .png files in case of sunflow rendering engine
        if f.endswith('.csv'):
            monitoring_files.append(os.path.join(path, f))
    if len(monitoring_files) == 0:
        print('no csv files found.')
        print(path)
        return None
    # print('number csv files:', len(monitoring_files))

    dfs = []
    for i, f in enumerate(monitoring_files):
        df = pd.read_csv(f, header=0)
        df = df.rename(columns={"Hot Spot": "m_name", "Self Time (microseconds)": "perf", "Invocations": "num"})
        df.pop('Average Time (microseconds)')
        # print(list(df))
        df['rep'] = i
        # print(list(df))
        # print(len(df))
        # exit(0)
        dfs.append(df)
    return pd.concat(dfs)


shared_array = []


def preprocess_jprofiler(path):
    cfgs = os.listdir(path)
    total_cfgs = len(cfgs)
    print('Num configs to process:', total_cfgs)
    count = 0

    num_cores = multiprocessing.cpu_count()

    num_cores = 1

    Parallel(n_jobs=num_cores, require='sharedmem')(delayed(parallel_jprofiler_parsing)(cfg, path, total_cfgs) for cfg in cfgs)
    shared_array.clear()

    # for cfg in cfgs:
    #     cfg_path = os.path.join(path, cfg)
    #     if not os.path.isdir(cfg_path):
    #         # sunflow image folder
    #         continue
    #     parse_jp_to_csv(cfg_path)
    #     count += 1
    #     print('Done:', count, '/', total_cfgs)


def parallel_jprofiler_parsing(cfg, path, total_cfgs):
    cfg_path = os.path.join(path, cfg)
    if not os.path.isdir(cfg_path):
        # sunflow image folder
        return
    parse_jp_to_csv(cfg_path)
    shared_array.append(1)
    print('Done:', len(shared_array), '/', total_cfgs)


def parse_jp_to_csv(path, refresh=True):
    # print(path)

    num_jps = len([f for f in os.listdir(path) if f.endswith('.jps')])
    num_csv = len([f for f in os.listdir(path) if f.endswith('.csv')])

    if num_jps == num_csv:
        print('Already parsed.')
        return

    files = os.listdir(path)

    for file in files:
        if '.jps' in file:
            # print(file)
            out_file = os.path.join(path, file[:-3]+'csv')
            if not os.path.exists(out_file) or refresh:
                # bash_template = Template('/opt/jprofiler/bin/jpexport ${input} HotSpots -format=csv ${output}')
                # bash_template = Template('/home/max/jprofiler11/bin/jpexport ${input} HotSpots -format=csv ${output}')
                # bash_template = Template('/home/mweber/jprofiler11/bin/jpexport ${input} HotSpots -format=csv ${output}')
                bash_template = Template(bash_template_string)
                bash_command = bash_template.substitute(
                    input=os.path.join(path, file),
                    output=os.path.join(path, file[:-3]+'csv')
                )
                os.system(bash_command)


# ---------------------------------------------------------------
# no profiler
# ---------------------------------------------------------------

def save_exp_to_pkl_no_prof(sub_sys, sam_str, p_name, exps):
    # sub_sys, sam_str, prof, experiment
    print('num', len(exps))
    sub_sys_name = sub_sys[sub_sys[:-1].rfind('/') + 1:-1]
    out_name = sub_sys_name + '__' + sam_str + '__' + p_name + '.pkl'
    print('name of plk', out_name)

    columns = ['perf', 'sam_strat', 'profiler', 'config', 'rep']
    res = []

    # iterate over configurations
    for ex in exps:
        cfg = ex
        pvals = exps[ex]

        for idx, val in enumerate(pvals):
            res.append([val, sam_str, p_name, cfg, idx])

    df = pd.DataFrame(res, columns=columns)
    df.to_pickle(processed_data_root + out_name)
    return df


def process_none_profiler_measurements(path):
    cfgs = os.listdir(path)
    print('Num configs to process:', len(cfgs))
    experiment = {}

    for cfg in cfgs:
        cfg_path = os.path.join(path, cfg)
        if not os.path.isdir(cfg_path):
            continue
        perf = read_repetitions(cfg_path)
        # print('data:', cfg, perf)
        experiment[cfg] = perf

    return experiment


def read_repetitions(c_folder):

    monitoring_files = []
    for f in os.listdir(c_folder):
        # rm .png files in case of sunflow rendering engine
        if f.endswith('.png'):
            continue
        elif f.endswith('bb_time.txt'):
            continue
        elif f.endswith('out.txt'):
            continue
        elif f.endswith('err.txt'):
            continue
        monitoring_files.append(os.path.join(c_folder, f))

    perf = []
    for file in monitoring_files:
        f = open(file, "r")
        perf.append(float(f.readlines()[-1]))
    return perf


def main():
    # MAIN
    for sub_sys in [catena_root, sunflow_root, h2_root, prevayler_root, pmd_root, density_root, cpd_root]:

    # traverse folder hirarcy
    # sub_sys
    # - sam_str - prof - cfgs - rep
    print('Start processing', sub_sys[sub_sys[:-1].rfind('/') + 1:-1])
    print()
    for sam_str in os.listdir(sub_sys):
        print('Start:', sam_str)

        sam_str_path = os.path.join(sub_sys, sam_str)

        for prof in os.listdir(sam_str_path):
            # there are predefined profiler
            # for modeling coarse grained performance use 'jip'

            if not prof == 'ProfilerKiekerArgs':
                # continue
                pass

            prof_path = os.path.join(sam_str_path, prof)

            if prof == 'ProfilerJIP':
                print('Start:', prof)
                experiment = process_jip_measurements(prof_path)
                df = save_exp_to_pkl(sub_sys, sam_str, prof, experiment)
                print('df size:', len(df))
                print('Fin:', prof)

            elif prof == 'ProfilerNone':
                print('Start:', prof)
                experiment = process_none_profiler_measurements(prof_path)
                df = save_exp_to_pkl_no_prof(sub_sys, sam_str, prof, experiment)
                print('df size:', len(df))
                print('Fin:', prof)

            elif prof == 'ProfilerKiekerArgs':
                print('Start:', prof)
                experiment = process_kieker_measurements(prof_path)
                df = save_exp_kieker_to_pkl(sub_sys, sam_str, prof, experiment)
                print('df size:', len(df))
                print('Fin:', prof)

            elif prof == 'jProfiler':
                print('Start', prof)
                preprocess_jprofiler(prof_path)
                df = process_jprofiler_measurements(prof_path)
                df['sam_strat'] = sam_str
                df['profiler'] = prof
                exp = save_exp_kieker_to_pkl(sub_sys, sam_str, prof, df)
                print(len(exp))
                print(list(exp))
                print('df size:', len(exp))
                print('Fin:', prof)

        print('Fin:', sam_str)
        print()
    print('Done.')


if __name__ == "__main__":
    main()
