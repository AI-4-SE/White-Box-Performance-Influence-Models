import os
import sys
import getopt
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

from sklearn import tree
from sklearn import linear_model
from sklearn.pipeline import make_pipeline
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import PolynomialFeatures
from sklearn.model_selection import train_test_split

#from system_config_parser import transform_config_single
from system_config_parser import transform_config
import warnings
import argparse



def make_train_test_split(df, percentage=0.2, x_identifier='config', y_identifier='perf'):
    train, test = train_test_split(df, test_size=percentage)

    x_train = train[x_identifier].values
    y_train = train[y_identifier].values
    x_test = test[x_identifier].values
    y_test = test[y_identifier].values

    return x_train, y_train, x_test, y_test

def make_x_y_split(df, x_identifier='config', y_identifier='perf'):
    x = df[x_identifier].values
    y = df[y_identifier].values

    return x, y

def mape(truth, prediction):
    values_abs = np.abs(truth - prediction)
    values_div = np.divide(values_abs, truth, out=np.zeros_like(values_abs), where=truth != 0)
    return np.mean(values_div)

def prediction_error(y_test, y_pred):
    return mape(y_test, y_pred)*10


def learn(dataframe, eval_df, sub_sys, learner='ridge', df_column='perf', pw_interactions=False):

    experiment_rep = 0

    experiment_reps = dataframe.rep.unique()
    if len(experiment_reps) == 1:
        experiment_rep = experiment_reps[0]
    else:
        raise Exception('too many repetitions to learn in a model')


    #x_train, y_train, x_test, y_test = make_train_test_split(dataframe, y_identifier=df_column)
    #x_train, x_test = transform_config(sub_sys, x_train, x_test)

    x_train, y_train = make_x_y_split(dataframe, y_identifier=df_column)
    x_test = []
    x_train, x_test = transform_config(sub_sys, x_train, x_test)

    x_test, y_test = make_x_y_split(eval_df, y_identifier=df_column)
    x_ = []
    x_test, _ = transform_config(sub_sys, x_test, x_)
    #x_test, y_test = read_eval(sub_sys, df_column, m)

    model = None

    if learner == 'ridge':
        model = linear_model.Ridge()
    elif learner == 'dectree':
        model = tree.DecisionTreeRegressor()
    elif learner == 'randomforest':
        model = RandomForestRegressor(random_state=0, n_estimators=20, n_jobs=5)
    else:
        print('specify learner for modeling')

    y_pred = []
    if pw_interactions:
        poly = PolynomialFeatures(2, interaction_only=True, include_bias=True)
        ppl = make_pipeline(poly, model)

        ppl.fit(x_train, y_train)
        y_pred = ppl.predict(x_test)
    else:
        model.fit(x_train, y_train)
        y_pred = model.predict(x_test)

    p_errors = prediction_error(y_test, y_pred)

    return p_errors

def jProfiler_modeling(file, system, evaluation_data):
    df = pd.read_pickle(file)
    # ['m_name', 'perf', 'num', 'rep', 'config', 'sam_strat', 'profiler']
    groups = df.groupby('m_name')
    print(len(groups))

    skipped_methods = []
    performance_of_methods = []
    methodnames = []
    errors_ridge = []
    errors_dec_tree = []
    errors_rf_ensemble = []
    configuration_variance = []

    errors_ridge_pw = []
    errors_dec_tree_pw = []
    errors_rf_ensemble_pw = []

    arr_repetitions = []

    df_out = pd.DataFrame(columns=['method', 'config', 'measured', 'pred'])

    for m, method_df in groups:
        method_df = method_df.dropna()
        print(m)

        # If there is too less data to event think of training a model
        # 50 -> 5 rep -> at least 2 elements in test set to test model
        if len(method_df) < 10:
            skipped_methods.append(m)
            #print('learn:', m, len(method_df))
            continue

        eval_df = df = pd.read_pickle(evaluation_data)

        repetitions = method_df.rep.unique()
        for r in repetitions:
            method_sub_df = method_df.loc[method_df['rep'] == r]

            methodnames.append(m)
            arr_repetitions.append(r)
            #if system == 'prevayler'
            errors_ridge.append(learn(method_sub_df, eval_df, system, learner='ridge'))
            errors_dec_tree.append(learn(method_sub_df, eval_df, system, learner='dectree'))
            errors_rf_ensemble.append(learn(method_sub_df, eval_df, system, learner='randomforest'))
            errors_ridge_pw.append(learn(method_sub_df, eval_df, system, learner='ridge', pw_interactions=True))

            performance_of_methods.append(method_sub_df['perf'].mean())
            configuration_variance.append(method_sub_df['perf'].std()/method_sub_df['perf'].mean())


    print('Number methods skipped:', len(skipped_methods), 'of', len(groups))
    #print(skipped_methods)
    d = {'m_name': methodnames, 'err_ridge': errors_ridge, 'err_dectree': errors_dec_tree,
         'err_rf': errors_rf_ensemble, 'perf': performance_of_methods, 'cv': configuration_variance,
         'err_ridge_pw': errors_ridge_pw, 'rep': arr_repetitions}
    return pd.DataFrame(data=d)


def kieker_modeling(file, system, evaluation_data):
    df = pd.read_pickle(file)
    # print(list(df))
    # 'name',
    # 'length_o', 'mean_o', 'median_o', 'std_o', 'sum_o',
    # 'length_r', 'mean_r', 'median_r', 'std_r', 'sum_r',
    # 'hist', 'bin_edges',
    # 'arg_el_net_err', 'arg_el_net_params', 'arg_el_net_coefs',
    # 'arg_dec_tree_err', 'arg_dec_tree_params', 'arg_dec_tree_tree_',
    # 'var_hist', 'rep', 'config'

    groups = df.groupby('name')
    print(len(groups))

    skipped_methods = []
    performance_of_methods = []
    methodnames = []
    errors_ridge = []
    errors_dec_tree = []
    errors_rf_ensemble = []
    configuration_variance = []

    errors_ridge_pw = []
    errors_dec_tree_pw = []
    errors_rf_ensemble_pw = []
    arr_repetitions = []

    df_out = pd.DataFrame(columns=['method', 'config', 'measured', 'pred'])

    for m, method_df in groups:
        #method_df = method_df.dropna()
        #print(method_df)
        print(m)

        # If there is too less data to event think of training a model
        # 10 -> at least 2 elements in test set to test model
        if len(method_df) < 10:
            skipped_methods.append(m)
            continue

        eval_df = df = pd.read_pickle(evaluation_data)
        
        repetitions = method_df.rep.unique()
        for r in repetitions:

            method_sub_df = method_df.loc[method_df['rep'] == r]
            if len(method_sub_df) < 10:
                continue

            methodnames.append(m)
            arr_repetitions.append(r)

            errors_ridge.append(learn(method_sub_df, eval_df, system, learner='ridge', df_column='sum_r'))
            errors_dec_tree.append(learn(method_sub_df, eval_df, system, learner='dectree', df_column='sum_r'))
            errors_rf_ensemble.append(learn(method_sub_df, eval_df, system, learner='randomforest', df_column='sum_r'))
            errors_ridge_pw.append(learn(method_sub_df, eval_df, system, learner='ridge', pw_interactions=True, df_column='sum_r'))

            performance_of_methods.append(method_sub_df['sum_r'].mean())
            configuration_variance.append(method_sub_df['sum_r'].std()/method_sub_df['std_r'].mean())

    print('Number methods skipped:', len(skipped_methods), 'of', len(groups))
    #print(len(methodnames), methodnames)
    #print(len(errors_ridge), errors_ridge)
    #print(len(errors_dec_tree), errors_dec_tree)
    #print(len(errors_rf_ensemble), errors_rf_ensemble)
    #print(len(performance_of_methods), performance_of_methods)
    #print(len(configuration_variance), configuration_variance)
    #print(len(errors_ridge_pw), errors_ridge_pw)
    #print(len(arr_repetitions), arr_repetitions)
    d = {'m_name': methodnames, 'err_ridge': errors_ridge, 'err_dectree': errors_dec_tree,
         'err_rf': errors_rf_ensemble, 'perf': performance_of_methods, 'cv': configuration_variance,
         'err_ridge_pw': errors_ridge_pw, 'rep': arr_repetitions}
    return pd.DataFrame(data=d)






def split_file(file):
    file = file[:-4]
    return file.split('__')


def main(training_data, evaluation_data, output_path):
    experiment_path, file = os.path.split(training_data)
    system, sampling, profiler = split_file(file)
    print(system, sampling, profiler)



    if profiler == 'jProfiler':
        print(system, sampling, profiler)
        df = jProfiler_modeling(os.path.join(experiment_path, file), system, evaluation_data)
        df.to_pickle(os.path.join(output_path, file[:-4]+'_model_acc.pkl'))

    elif profiler == 'ProfilerKiekerArgs':
        print(system, sampling, profiler)
        df = kieker_modeling(os.path.join(experiment_path, file), system, evaluation_data)
        #df.to_pickle(os.path.join(output_path, file[:-4]+'_model_acc.pkl'))

    else:
        print('Profiler not supported.')
    print()


if __name__ == "__main__":

    argv = sys.argv[1:]
    if len(argv) != 3:
        print('learn_method_level_model.py <in_file> <eval_file> <output_path>')
        print('learn_method_level_model.py ./../../experiment_data/data/density-converter__t_2_pbd_49_7__jProfiler.pkl.gz ./../../experiment_data/data/density-converter__rnd100__jProfiler.pkl /tmp/')
        sys.exit(2)

    training_data = argv[0]
    evaluation_data = argv[1]
    output_path = argv[2]
    main(training_data, evaluation_data, output_path)
