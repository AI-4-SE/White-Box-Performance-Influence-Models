#!python3

import os
import time
import subprocess
from datetime import datetime
from profiling.abstractprofiler import AbstractProfiler


class ProfilerNone(AbstractProfiler):

    def __init__(self, executable, software_name, sampling_strategy, cfg):
        # decide which executable should be picked for profiling
        #  - with or without annotations
        self.name = 'ProfilerNone'
        super().__init__(executable[0], software_name, sampling_strategy, self.name, cfg)
        self.command = []

    def create_instance(self):
        pass

    def profile(self, iteration, stdout, stderr):
        # print('creating profiling instance for None Profiler')
        self.command = AbstractProfiler.append_config_parameter(['java', '-jar', self.executable], self.cfg)

        # TODO: in case of pmd the cli interface is not the same
        if self.software_name == 'pmd':
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run.sh', 'pmd'], self.cfg)

        # TODO: in case of cpd the cli interface is not the same
        if self.software_name == 'cpd':
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run.sh', 'cpd'], self.cfg)

        print('execute profile', self.command)
        print('here:', os.getcwd())
        start_time = time.time()
        subprocess.call(self.command, stdout=stdout, stderr=stderr)
        end_time = time.time()

        self.save_profiling_data(end_time - start_time)

    def save_profiling_data(self, data):
        print('saving data to', self.output_path, ' - ', data)
        date_time_obj = datetime.now()
        out_file_name = date_time_obj.strftime("%Y%m%d-%H%M%S")
        out_file = os.path.join(self.output_path, out_file_name + '.txt')
        with open(out_file, 'w') as f:
            f.write(str(data))
