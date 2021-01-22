#!python3

import sys
from configparser import ConfigParser
from profiling.profilerfactory import ProfilerFactory


def get_sampling_name(config_path):
    find_str = '_config_'
    idx = config_path.find(find_str)
    sampling_name = config_path[idx+len(find_str):-4]
    return sampling_name


def main(args):

    software_name = ''
    config = ''
    sampling_strategy = ''
    profiler_name = ''
    rep = 0

    if len(args) == 6:
        config = args[1]
        software_name = args[2]
        sampling_strategy = args[3]
        profiler_name = args[4]
        rep = args[5]
    else:
        print('not enough parameters to start profiling')

    print(software_name, config, sampling_strategy, profiler_name, rep)

    # translating configurations
    cfg_parser = ConfigParser(software_name)
    parsed_cfg = cfg_parser.parse(config)
    print(parsed_cfg)
    executables = [cfg_parser.software_system.executable, cfg_parser.software_system.executable_annotations]

    profiler = ProfilerFactory(profiler_name, executables, software_name, sampling_strategy, parsed_cfg)
    profiler.execute(rep=rep)

    # testing area:
    print('execute performance measurement with following params:')
    print('configuration:', config)
    print('parsed cfg:', parsed_cfg)
    print('sampling strategy name:', sampling_strategy)
    print('name of software system and rep:', software_name, rep)
    print('profiler:', profiler_name)
    print('name of corresponding executables:', executables)


if __name__ == "__main__":
    main(sys.argv)
