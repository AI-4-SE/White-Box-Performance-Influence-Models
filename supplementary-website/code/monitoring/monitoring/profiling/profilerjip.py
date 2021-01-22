#!python3

import os
import subprocess

from profiling.abstractprofiler import AbstractProfiler


class ProfilerJIP(AbstractProfiler):

    def __init__(self, executable, software_name, sampling_strategy, cfg):
        # decide which executable should be picked for profiling
        #  - with or without annotations
        self.name = 'ProfilerJIP'
        super().__init__(executable[0], software_name, sampling_strategy, self.name, cfg)
        self.command = []

        self.profiler_properties = 'profile_lib/jip_properties/adaptedprofile.properties'
        self.java_agent = "-javaagent:profile_lib/profile.jar"
        self.prof_prop_adapted = ''

    def create_instance(self):
        # adapt profiling properties
        profile_prop_path = os.path.join(self.project_root, self.profiler_properties)
        output = ''

        print('try to add output path to properties file:', self.output_path)

        with open(profile_prop_path, "r") as f:
            for line in f:
                if "file=" in line:
                    output += "file=" + self.output_path + "\n"
                    print('Found it')
                else:
                    output += line

        self.prof_prop_adapted = profile_prop_path + "__" + AbstractProfiler.join_config(self.cfg)
        with open(self.prof_prop_adapted, "w") as text_file:
            text_file.write(output)

    def profile(self, iteration, stdout, stderr):
        print('creating profiling instance for JIP Profiler')
        print('current working directory:', os.getcwd())

        parameter_array = ['java',
                           self.java_agent,
                           '-noverify',
                           '-Dprofile.properties='+self.prof_prop_adapted,
                           '-jar',
                           self.executable]

        local_config = self.cfg
        self.command = AbstractProfiler.append_config_parameter(parameter_array, local_config)

        # TODO: in case of pmd the cli interface is not the same
        if self.software_name == 'pmd':
            print('Adapt shell script to weave jip pmd')
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run_jip.sh', 'pmd',
                                                                     self.prof_prop_adapted], self.cfg)
        # TODO: in case of cpd the cli interface is not the same
        if self.software_name == 'cpd':
            print('Adapt shell script to weave jip cpd')
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run_jip.sh', 'cpd',
                                                                     self.prof_prop_adapted], self.cfg)

        print('execute profile', self.command)
        subprocess.call(self.command, stdout=stdout, stderr=stderr)

        print('done profiling iteration', iteration)
