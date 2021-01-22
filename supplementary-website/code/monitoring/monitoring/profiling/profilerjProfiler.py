from profiling.abstractprofiler import AbstractProfiler

import os
import subprocess
from string import Template


class ProfilerjProfiler(AbstractProfiler):

    def __init__(self, executable, software_name, sampling_strategy, cfg):
        # decide which executable should be picked for profiling
        #  - with or without annotations
        self.name = 'jProfiler'
        super().__init__(executable[0], software_name, sampling_strategy, self.name, cfg)
        self.command = []

        self.jProfiler_root = '/media/hdd/max/data/'

        self.profiler_properties = '.jprofiler11/jp_config/jprofiler_config_template.xml'
        self.profiler_binary = os.path.join(self.jProfiler_root, 'jprofiler11/bin/linux-x64/libjprofilerti.so')
        self.java_agent = "-agentpath:" + self.profiler_binary
        self.prof_prop_adapted = ''

    def create_instance(self):
        # adapt profiling properties
        profile_prop_path = os.path.join(self.project_root, self.profiler_properties)
        output = ''

        print('read config file:', profile_prop_path)
        print('try to add output path to properties file:', self.output_path)

        with open(profile_prop_path, 'r') as myfile:
            data = Template(myfile.read())
            output = data.substitute(output_path=self.output_path+'/'+self.software_name)

        self.prof_prop_adapted = profile_prop_path + "__" + AbstractProfiler.join_config(self.cfg)
        with open(self.prof_prop_adapted, "w") as text_file:
            text_file.write(output)

    def profile(self, iteration, stdout, stderr):
        print('creating profiling instance for jProfiler')
        print('current working directory:', os.getcwd())

        parameter_array = ['java',
                           self.java_agent + '=offline,id=112,config=' + self.prof_prop_adapted,
                           '-jar',
                           self.executable]

        local_config = self.cfg
        self.command = AbstractProfiler.append_config_parameter(parameter_array, local_config)

        # TODO: in case of pmd the cli interface is not the same
        if self.software_name == 'pmd':
            print('Adapt shell script to weave jProfiler pmd')
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run_jProfiler.sh', 'pmd',
                                                                     self.prof_prop_adapted], self.cfg)
        # TODO: in case of cpd the cli interface is not the same
        if self.software_name == 'cpd':
            print('Adapt shell script to weave jProfiler cpd')
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run_jProfiler.sh', 'cpd',
                                                                     self.prof_prop_adapted], self.cfg)

        print('execute profile', self.command)
        subprocess.call(self.command, stdout=stdout, stderr=stderr)

        print('done profiling iteration', iteration)
