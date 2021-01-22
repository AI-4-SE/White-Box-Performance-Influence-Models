#!python3

import os
import pandas as pd
import seaborn as sns
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as sps
from sklearn import tree
from sklearn import linear_model
import itertools
from sklearn.model_selection import train_test_split

np.random.seed(123456)


experiment_path = '/run/media/[name]/6650AF2E50AF0441/experiment_data/'


# ---------------------------------------------------------------
# WB - JIP
# ---------------------------------------------------------------


def process_jip_measurements(path, system):
    # print('jip')
    df = pd.read_pickle(path)
    df['mean_perf'] = df['perf'] / df['num']

    df = df.groupby(['m_name', 'sam_strat', 'profiler', 'config'])['mean_perf', 'perf'].agg(np.mean)  # .to_frame()
    df.reset_index(inplace=True)
    df.drop(['sam_strat', 'profiler'], axis=1, inplace=True)

    groups = df.groupby('m_name')
    names_of_methods = []
    errors_of_methods = []
    performance_of_methods = []
    skipped_methods = []

    df_out = pd.DataFrame(columns=['method', 'config', 'measured', 'pred'])

    # iterate over different methods
    for name, group in groups:

        group = group.dropna()

        # If there is too less data to event think of training a model
        # 10 -> at least 2 elements in test set to test model
        if len(group) < 10:
            skipped_methods.append(name)
            continue

        names_of_methods.append(name)
        learning_output = learn(group, sub_sys=system)

        accuracy = learning_output[0]
        df_tmp = learning_output[1]
        df_tmp['method'] = name
        df_out = df_out.append(df_tmp, sort=True)

        errors_of_methods.append(accuracy)
        performance_of_methods.append(group['perf'].mean())

    # print('skipped:', skipped_methods)
    d = {'m_name': names_of_methods, 'err': errors_of_methods, 'perf': performance_of_methods}
    return pd.DataFrame(d), df_out


# ---------------------------------------------------------------
# WB2
# ---------------------------------------------------------------

def process_kieker_measurements(path, system):
    df = pd.read_pickle(path)

    df.reset_index(inplace=True)
    df.drop(['hist', 'bin_edges', 'arg_el_net_err', 'arg_el_net_params', 'arg_el_net_coefs',
             'arg_dec_tree_err', 'arg_dec_tree_params', 'arg_dec_tree_tree_', 'var_hist', ], axis=1, inplace=True)

    groups = df.groupby('name')

    names_of_methods = []
    skipped_methods = []
    errors_of_methods_mean = []
    errors_of_methods_median = []

    df_out = pd.DataFrame(columns=['method', 'config', 'measured', 'pred'])

    for name, group in groups:

        names_of_methods.append(name)
        group = group.dropna()

        # If there is too less data to event think of training a model
        # 10 -> at least 2 elements in test set to test model
        if len(group) < 10:
            skipped_methods.append(name)
            continue

        learning_output_median = learn(group, system, repetitions=5, df_column='median_r')
        accuracy_median = learning_output_median[0]
        df_tmp = learning_output_median[1]
        df_tmp['method'] = name
        df_out = df_out.append(df_tmp, sort=True, )

        errors_of_methods_median.append(accuracy_median)

        learning_output_mean = learn(group, system, repetitions=5, df_column='mean_r')
        accuracy_mean = learning_output_mean[0]
        # print('cfgs:', len(configs), 'predicted:', len(predicted_values))
        errors_of_methods_mean.append(accuracy_mean)

    print('skipped:', skipped_methods)
    d = {'m_name': names_of_methods, 'err_mean': errors_of_methods_mean, 'err_median': errors_of_methods_median}
    return pd.DataFrame(d), df_out


# ---------------------------------------------------------------
# jProfiler
# ---------------------------------------------------------------


def process_jprofiler_measurements(path, system):
    df = pd.read_pickle(path)
    print(list(df))
    groups = df.groupby('m_name')

    skipped_methods = []
    names_of_methods = []
    errors_of_methods = []
    performance_of_methods = []

    df_out = pd.DataFrame(columns=['method', 'config', 'measured', 'pred'])

    for m, method_df in groups:
        print(m)

        method_df = method_df.dropna()

        # If there is too less data to event think of training a model
        # 10 -> at least 2 elements in test set to test model
        if len(method_df) < 10:
            skipped_methods.append(m)
            continue

        names_of_methods.append(m)
        learning_output = learn(method_df, sub_sys=system)

        accuracy = learning_output[0]
        df_tmp = learning_output[1]
        df_tmp['method'] = m
        df_out = df_out.append(df_tmp, sort=True)

        errors_of_methods.append(accuracy)
        performance_of_methods.append(method_df['perf'].mean())

    # print('skipped:', skipped_methods)
    d = {'m_name': names_of_methods, 'err': errors_of_methods, 'perf': performance_of_methods}
    return pd.DataFrame(d), df_out


# ---------------------------------------------------------------
# BB
# ---------------------------------------------------------------


def process_bb_measurements(path, system):
    # print('bb')
    df = pd.read_pickle(path)
    # print(len(df))
    learning_output = learn(df, system, repetitions=5)
    accuracy = learning_output[0]
    out_df = learning_output[1]
    out_df['method'] = ''

    return accuracy, out_df


def overhead_bb(path):
    df = pd.read_pickle(path)
    return df[['perf', 'config']]


# ---------------------------------------------------------------
# GENERAL
# ---------------------------------------------------------------


def learn(dataframe, sub_sys, repetitions=5, df_column='perf'):

    errors = []
    predicted_values = []
    measured_values = []
    configs = []
    for _ in range(repetitions):

        x_train, y_train, x_test, y_test = make_train_test_split(dataframe, y_identifier=df_column)

        x_train_d = x_train
        y_train_d = y_train
        x_test_d = x_test
        y_test_d = y_test

        x_train, x_test = transform_config(sub_sys, x_train, x_test)

        x_train = [[int(val) for val in tmp] for tmp in x_train]
        # y_train = [[int(val) for val in tmp] for tmp in y_train]
        x_test = [[int(val) for val in tmp] for tmp in x_test]
        # y_test = [[int(val) for val in tmp] for tmp in y_test]

        # TODO: pairwise interactions

        # model = tree.DecisionTreeRegressor()
        # model.fit(x_train, y_train)
        # y_pred = model.predict(x_test)

        model = linear_model.Ridge()
        model.fit(x_train, y_train)
        y_pred = []
        try:
            y_pred = model.predict(x_test)
        except TypeError as e:
            print(e)

            print(x_test_d)
            print(x_train_d)
            print(y_test_d)
            print(y_train_d)

            print(x_test)
            print(x_train)

        if len(y_pred) == 0:
            print()
            print()
            print('Y_PRED ... ')
            print(y_pred)

        p_errors = prediction_error(y_test, y_pred)
        # print(p_errors)
        errors.append(p_errors)

        # correlation analysis
        configs = list(x_test)
        #configs += list(x_train)

        measured_values = list(y_test)
        #measured_values += list(y_train)

        predicted_values = list(model.predict(x_test))
        #predicted_values += list(model.predict(x_train))

    data = []
    for i in range(len(configs)):
        data.append([configs[i], measured_values[i], predicted_values[i]])

    df = pd.DataFrame(data=data, columns=['config', 'measured', 'pred'])
    error = np.mean(errors)
    return [error, df]


def make_train_test_split(df, percentage=0.2, x_identifier='config', y_identifier='perf'):
    train, test = train_test_split(df, test_size=percentage)

    x_train = train[x_identifier].values
    y_train = train[y_identifier].values
    x_test = test[x_identifier].values
    y_test = test[y_identifier].values

    return x_train, y_train, x_test, y_test


def mae(truth, prediction):
    return np.mean(np.abs(truth - prediction))


def mape(truth, prediction):
    values_abs = np.abs(truth - prediction)
    values_div = np.divide(values_abs, truth, out=np.zeros_like(values_abs), where=truth != 0)
    return np.mean(values_div)


def prediction_error(y_test, y_pred):
    return mape(y_test, y_pred)


def extract_properties_from_name(name):
    return name[:-4].split('__')


def transform_config(sub_sys, x_train, x_test):
    # copied from Notebook "Coarse Grained PIM"
    if sub_sys == 'catena':
        x_train = [parse_config_catena(x) for x in x_train]
        x_test = [parse_config_catena(x) for x in x_test]

        x_train = [catena_config_transformation(x_tr) for x_tr in x_train]
        x_test = [catena_config_transformation(x_te) for x_te in x_test]

    elif sub_sys == 'h2':
        x_train = [parse_config_h2(x) for x in x_train]
        x_test = [parse_config_h2(x) for x in x_test]

    elif sub_sys == 'sunflow':
        x_train = [parse_config_sunflow(x) for x in x_train]
        x_test = [parse_config_sunflow(x) for x in x_test]

    elif sub_sys == 'density-converter':
        x_train = [parse_config_density(x) for x in x_train]
        x_test = [parse_config_density(x) for x in x_test]

    elif sub_sys == 'pmd':
        x_train = [parse_config_pmd(x) for x in x_train]
        x_test = [parse_config_pmd(x) for x in x_test]

    elif sub_sys == 'cpd':
        x_train = [parse_config_cpd(x) for x in x_train]
        x_test = [parse_config_cpd(x) for x in x_test]

    elif sub_sys == 'prevayler':
        x_train = [parse_config_prevayler(x) for x in x_train]
        x_test = [parse_config_prevayler(x) for x in x_test]

    else:
        print('no config transformation defined for', sub_sys)
        raise Exception('No config parser defined')
    return x_train, x_test


def parse_config_prevayler(cfg):
    # YES_NO_NO_ONE_MILLION_1_5_2_2
    out = []
    splited = cfg.split('_')

    if splited[0] == 'YES':
        out += [1]
    else:
        out += [0]
    if splited[1] == 'YES':
        out += [1]
    else:
        out += [0]
    if splited[2] == 'YES':
        out += [1]
    else:
        out += [0]

    if 'THOUSAND' in splited:
        out += [1, 0]
    else:
        out += [0, 1]

    out += splited[-4]
    out += splited[-3]
    out += splited[-2]
    out += splited[-1]

    # print(cfg)
    # print(out)

    return out


def parse_config_pmd(cfg):
    # example:
    # cfg-d_system_to_imvestigate_prevayler-_-R_softwareConfs-quickstart.xml_-format_html_-threads_2
    out = []

    if '-format_html' in cfg:
        out += [1, 0, 0, 0, 0]
    elif '-format_csv' in cfg:
        out += [0, 1, 0, 0, 0]
    elif '-format_emacs' in cfg:
        out += [0, 0, 1, 0, 0]
    elif '-format_summaryhtml' in cfg:
        out += [0, 0, 0, 1, 0]
    elif '-format_codeclimate' in cfg:
        out += [0, 0, 0, 0, 1]

    if 'cache' in cfg:
        no_identifier = cfg[cfg.find('cache')-1]
        if no_identifier == '-':
            out += [1]
        else:
            out += [0]
    else:
        out += [0]
    if 'shortnames' in cfg:
        out += [1]
    else:
        out += [0]
    if 'showsuppressed' in cfg:
        out += [1]
    else:
        out += [0]
    if 'shortnames' in cfg:
        out += [1]
    else:
        out += [0]
    if 'no_cache' in cfg:
        out += [1]
    else:
        out += [0]

    numbers = cfg[cfg.find('-threads'):].split('_')
    out += [numbers[1]]

    return out


def parse_config_cpd(cfg):
    out = []

    if 'skip_duplicate_files' in cfg:
        out += [1]
    else:
        out += [0]

    if 'failOnViolation' in cfg:
        out += [1]
    else:
        out += [0]

    if 'ignore-literals' in cfg:
        out += [1]
    else:
        out += [0]

    if 'ignore-identifiers' in cfg:
        out += [1]
    else:
        out += [0]

    if 'ignore-annotations' in cfg:
        out += [1]
    else:
        out += [0]

    if 'format_text' in cfg:
        out += [1, 0, 0, 0, 0]
    elif 'format_csv' in cfg:
        out += [0, 1, 0, 0, 0]
    elif 'format_csv_with_linecount_per_file' in cfg:
        out += [0, 0, 1, 0, 0]
    elif 'format_xml' in cfg:
        out += [0, 0, 0, 1, 0]
    elif 'format_vs' in cfg:
        out += [0, 0, 0, 0, 1]

    vals = cfg[cfg.find('minimum-tokens'):].split('_')
    out += [vals[1]]

    return out


def parse_config_density(cfg):
    # example:
    # cfg-src_-media-raid-[name]-data-fosd1029-_-dst_-media-hdd-[name]-data_output-_
    # -platform_android_
    # -outCompression_png_
    # -roundingMode_round_
    # -compressionQuality_0.0_-scale_49_-threads_1

    # print(cfg)
    out = []

    if '-platform_all' in cfg:
        out += [1, 0, 0, 0, 0]
    elif '-platform_android' in cfg:
        out += [0, 1, 0, 0, 0]
    elif '-platform_win' in cfg:
        out += [0, 0, 1, 0, 0]
    elif '-platform_web' in cfg:
        out += [0, 0, 0, 1, 0]
    elif '-platform_ios' in cfg:
        out += [0, 0, 0, 0, 1]

    if 'androidIncludeLdpiTvdpi' in cfg:
        out += [1]
    else:
        out += [0]
    if 'androidMipmapInsteadOfDrawable' in cfg:
        out += [1]
    else:
        out += [0]
    if 'antiAliasing' in cfg:
        out += [1]
    else:
        out += [0]
    if 'iosCreateImagesetFolders' in cfg:
        out += [1]
    else:
        out += [0]
    if 'keepOriginalPostProcessedFiles' in cfg:
        out += [1]
    else:
        out += [0]

    if '-outCompression_png' in cfg:
        out += [1, 0, 0, 0]
    elif '-outCompression_bmp' in cfg:
        out += [0, 1, 0, 0]
    elif '-outCompression_gif' in cfg:
        out += [0, 0, 1, 0]
    elif '-outCompression_jpg' in cfg:
        out += [0, 0, 0, 1]

    if '-roundingMode_round' in cfg:
        out += [1, 0, 0]
    elif '-roundingMode_ceil' in cfg:
        out += [0, 1, 0]
    elif '-roundingMode_floor' in cfg:
        out += [0, 0, 1]

    if 'skipExisting' in cfg:
        out += [1]
    else:
        out += [0]

    numbers = cfg[cfg.find('-compressionQuality'):].split('_')
    out += [numbers[1], numbers[3], numbers[5]]
    return out


def parse_config_h2(cfg):
    # 2001_5001_0_0_0_0_1_0_0_0_0_0_0_0_0_0_50000
    # [2001, 5001, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    return cfg.split('_')[:15]


def parse_config_sunflow(cfg):
    # 1_21_0_0_0_0_128
    # [1, 21, 0, 0, 0, 0, 128]
    return cfg.split('_')[:5]


def parse_config_catena(cfg):
    # cfg =  1_0_1_0_10_49_0_64_Butterfly-Full-adapted_6789ab_6789ab_6789ab_64
    # out = [1 0 1 0 10 49 0 64]
    return cfg.split('_')[:8]


def catena_config_transformation(x):
    # translates HASH and GRAPH back into independent features
    # [hash, gamma, graph, phi, garlic, lambda, v_id, d]
    # [1.0, 0.0, 1.0, 0.0, 4.0, 49.0, 64.0, 192.0] ->
    # [1.0, 0.0, 1.0, 0.0, 4.0, 49.0, 64.0, 192.0]
    # print('before:',X)
    transformed_x = []

    for i, feature in enumerate(x):
        # print('i',i,'feature',feature, type(feature))
        if i == 0:
            if feature == '1':
                transformed_x = transformed_x + [1.0, 0.0]
            else:
                transformed_x = transformed_x + [0.0, 1.0]
        elif i == 2:
            if feature == '1':
                transformed_x = transformed_x + [1.0, 0.0, 0.0, 0.0]
            elif feature == '2':
                transformed_x = transformed_x + [0.0, 1.0, 0.0, 0.0]
            elif feature == '3':
                transformed_x = transformed_x + [0.0, 0.0, 0.0, 1.0]
            elif feature == '4':
                transformed_x = transformed_x + [0.0, 0.0, 1.0, 0.0]
        else:
            transformed_x.append(float(feature))
        # print(transformed_X)
    # print('after:',transformed_X)
    return transformed_x

# ---------------------------------------------------------------
# FILTER
# ---------------------------------------------------------------


def filter_methods(df, system, sampling):
    abs_perf = [1, 5, 10, 50, 100, 500, 1000]
    rel_perf = [10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05, 0.02, 0.01, 0.005, 0.002, 0.001]

    print('Total size:', len(df))
    new_df = df.loc[df['err'] > 0.1]
    print(len(new_df))

    out = []

    for abs_p in abs_perf:
        abs_perf_vals = []
        for rel_p in rel_perf:

            total_time = new_df['perf'].sum()
            filtered_df = new_df.loc[(new_df['perf'] > abs_p) & (new_df['perf']*100/total_time > rel_p)]
            # filtered_df = new_df.loc[(new_df['perf'] > abs_p) & (new_df['err'] > rel_p)]
            # out.append([abs_p, rel_p, len(filtered_df)])
            # print(abs_p, rel_p, len(filtered_df))
            if abs_p == 10 and rel_p == 0.5:
                print(len(filtered_df))
                print(filtered_df.values)

            abs_perf_vals.append(len(filtered_df))
        out.append(abs_perf_vals)

    # print(out)
    out_df = pd.DataFrame(out).T

    # sns.heatmap(out_df)
    # plt.show()
    # cmap = sns.color_palette("coolwarm", 128)
    plt.figure(figsize=(8, 6))
    ax1 = sns.heatmap(out_df, annot=True, yticklabels=rel_perf, xticklabels=abs_perf)
    plt.xlabel('Absolute Runtime in %')
    plt.ylabel('Relative Runtime in %')
    plt.title(' - '.join([system, sampling]))
    plt.show()
    ax1.figure.savefig(''.join(['/home/[name]/meeting/18.07.2019/filter_difficult/', system, sampling, '.png']),
                       bbox_inches="tight")

# ---------------------------------------------------------------
# Statistics
# ---------------------------------------------------------------


def center_x_axis(y_values):
    width = y_values[1]-y_values[0]
    return [n+(width/2) for n in y_values][:-1]


def fitness_proportionate_selection(hist, bins_centered, number_samples):
    total_method_calls = sum(hist)
    # print('total method calls:', total_method_calls)

    if total_method_calls <= number_samples:
        values = np.array([[single_bin] * single_hist for single_bin, single_hist in zip(bins_centered, hist)])
        liste = []
        for el in values:
            for e in el:
                liste.append(e)
        return liste

    out = []
    for i in range(number_samples):
        upperbound = total_method_calls-i
        idx = np.random.randint(0, upperbound-1)

        val = 0
        for index in range(len(hist)):
            curr = hist[index]
            idx = idx-curr
            if idx < 0:
                val = index
                break
        out.append(bins_centered[val])
    return out


def calculate_simialrities(path, system):
    """
    Returns:    - Array of Correlation Values of the 5 repetitions against each other
                - Array of Correlation Values between the different configurations
    """
    df = pd.read_pickle(path)

    df.reset_index(inplace=True)
    df = df[['name', 'config', 'hist', 'bin_edges', 'rep']]

    groups = df.groupby(['name', 'config'])
    skipped_methods = []
    # print('grouplen', len(groups))

    self_corr_of_methods = []

    # ----------------------------------------------------------------------
    # Start Correlation analysis of methods against each other
    # ----------------------------------------------------------------------

    groups = df.groupby(['name'])
    skipped_methods = []
    print('|M|', len(groups))

    inter_corr_of_methods = []

    for method_name, method_group in groups:
        rep_cfgs = len(method_group['hist'])
        print(list(method_group))
        print('cfg * rep', rep_cfgs)
        dists_of_rep_cfgs = []

        config_groups = method_group.groupby(['config'])

        # iterate over vonfigurations
        for cfg, cfg_groug in config_groups:
            config_dist = []

            # iterate over repetitions
            for i in range(len(cfg_groug)):
                hist = list(cfg_groug['hist'])[i]
                bins = list(cfg_groug['bin_edges'])[i]
                bins = center_x_axis(bins)
                sample = fitness_proportionate_selection(hist=hist, bins_centered=bins, number_samples=10)
                config_dist += sample
            dists_of_rep_cfgs.append(config_dist)

        combinations = list(itertools.combinations(dists_of_rep_cfgs, r=2))
        print(len(combinations))
        print(combinations[0])
        print(combinations[1])
        print(combinations[2])
        corrs, ps = correlation_of_all_combinations(combinations)
        print(corrs, ps)

    #
        inter_corr_of_methods += corrs
    #     #print(name, np.mean(corrs))
    #     #print(corrs)
    inter_corr_of_methods = [el for el in inter_corr_of_methods if not np.isnan(el)]

    return self_corr_of_methods, inter_corr_of_methods


def correlation_of_all_combinations(combinations, test='spearmanr'):
    """

    :param combinations: list of tuples
    :param test: (str) spearmanr, ks
    :return:    (float) corrs - correlation value
                (float) ps - pvalue
    """
    corrs = []
    ps = []

    for subset in combinations:

        a, b = subset
        if len(a) != len(b):
            continue

        if test == 'spearmanr':
            corr, p = sps.spearmanr(a, b)

        elif test == 'ks':
            corr, p = sps.ks_2samp(a, b)
        else:
            corr, p = sps.spearmanr(a, b)

        if not np.any(np.isnan(corr)):
            corrs.append(corr)
            ps.append(p)

    return corrs, ps


# ---------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------

def abort_early(sampling, df_name, system):
    if sampling != 'feature_pbd_125_5':
        # return True
        pass

    if system != 'cpd':
        # return True
        pass

    if 'ProfilerKiekerArgs' not in df_name:
        # return True
        pass

    if 'ProfilerNone' not in df_name:
        # return True
        pass

    if 'jProfiler' not in df_name:
        return True

    return False


def main(plot_filter, similarity_analysis):

    print('start creating PIMs')
    dataframes = os.listdir(experiment_path)
    print('number dfs:', len(dataframes))

    inter_correlation_values = []

    correlation_analysis_data = pd.DataFrame(columns=['system', 'sampling', 'profiler', 'method', 'config', 'measured',
                                                      'pred'])

    for df_name in dataframes:
        system, sampling, prof = extract_properties_from_name(df_name)

        if abort_early(sampling, df_name, system):
            continue

        if 'ProfilerNone' in df_name:
            acc, corr_df = process_bb_measurements(os.path.join(experiment_path, df_name), system)
            corr_df['system'] = system
            corr_df['sampling'] = sampling
            corr_df['profiler'] = prof

            print(system, sampling, prof, 'RESULT DF', list(corr_df), len(corr_df))
            correlation_analysis_data = correlation_analysis_data.append(corr_df, sort=True)
            print(acc, ' - ', df_name)

        elif 'jProfiler' in df_name:
            print(df_name)

            res_df, corr_df = process_jprofiler_measurements(os.path.join(experiment_path, df_name), system)
            corr_df['system'] = system
            corr_df['sampling'] = sampling
            corr_df['profiler'] = prof

            # print(system, sampling, prof, 'RESULT DF', list(corr_df), len(corr_df))
            correlation_analysis_data = correlation_analysis_data.append(corr_df, sort=True)
            print(res_df['err'].mean(), ' - ', res_df['perf'].mean(), ' - ', df_name)

            # print(list(res_df['err']))
            # print(list(res_df['m_name']))

            if plot_filter:
                filter_methods(res_df, system, sampling)

        elif 'ProfilerJIP' in df_name:
            res_df, corr_df = process_jip_measurements(os.path.join(experiment_path, df_name), system)
            corr_df['system'] = system
            corr_df['sampling'] = sampling
            corr_df['profiler'] = prof

            print(system, sampling, prof, 'RESULT DF', list(corr_df), len(corr_df))
            correlation_analysis_data = correlation_analysis_data.append(corr_df, sort=True)
            print(res_df['err'].mean(), ' - ', res_df['perf'].mean(), ' - ', df_name)

            if plot_filter:
                filter_methods(res_df, system, sampling)

        elif 'ProfilerKiekerArgs' in df_name:
            res_df, corr_df = process_kieker_measurements(os.path.join(experiment_path, df_name), system)
            corr_df['system'] = system
            corr_df['sampling'] = sampling
            corr_df['profiler'] = prof

            print(system, sampling, prof, 'RESULT DF', list(corr_df), len(corr_df))
            correlation_analysis_data = correlation_analysis_data.append(corr_df, sort=True)
            print(res_df['err_mean'].mean(), ' - ', res_df['err_median'].mean(), ' - ',  df_name)

            if similarity_analysis:
                print('similarities')
                sim_self, sim_inter = calculate_simialrities(os.path.join(experiment_path, df_name), system)
                print('self correlation', np.mean(sim_self))
                print('inter correlation', np.mean(sim_inter))

                for val in sim_self:
                    inter_correlation_values.append([system, sampling, prof, 'self', val])

                for val in sim_inter:
                    inter_correlation_values.append([system, sampling, prof, 'inter', val])

        else:
            print('Unhandled dataframe type - ', df_name)

        print(system, sampling, prof)
        print()

    if similarity_analysis:
        df = pd.DataFrame(inter_correlation_values, columns=['system', 'sampling', 'profiler', 'sim', 'value'])
        df.to_pickle('/home/[name]/Desktop/correlation.pkl')

    correlation_analysis_data.to_pickle('/home/[name]/Desktop/correlation_raw_data.pkl')


if __name__ == "__main__":
    plt_filter = False
    sim_analysis = False
    main(plt_filter, sim_analysis)
