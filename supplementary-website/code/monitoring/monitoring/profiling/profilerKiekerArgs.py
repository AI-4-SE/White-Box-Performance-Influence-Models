#!python3

import os
import subprocess
from profiling.abstractprofiler import AbstractProfiler


class ProfilerKiekerArgs(AbstractProfiler):
    def __init__(self, executable, software_name, sampling_strategy, cfg):
        # decide which executable should be picked for profiling
        #  - with or without annotations
        self.name = 'ProfilerKiekerArgs'
        super().__init__(executable[1], software_name, sampling_strategy, self.name, cfg)
        self.command = []

        self.profiler_properties = 'build/META-INF/kieker.monitoring.properties'
        self.aopXML = 'build/META-INF/aop.xml'
        self.java_agent = '-javaagent:profile_lib/kieker-1.14-SNAPSHOT-aspectj.jar'
        self.skipDefaultAOP = '-Dkieker.monitoring.skipDefaultAOPConfiguration=true'
        self.prof_prop_adapted = ''

    def create_instance(self):

        identifier_str = 'kieker.monitoring.writer.filesystem.AsciiFileWriter.customStoragePath='

        # adapt profiling properties
        profile_prop_path = os.path.join(self.project_root, self.profiler_properties)

        print(self.project_root, self.profiler_properties)
        print(profile_prop_path)

        output = ''
        with open(profile_prop_path, "r") as f:
            for line in f:
                if identifier_str in line:
                    output += identifier_str + self.output_path + "\n"
                else:
                    output += line

        self.prof_prop_adapted = profile_prop_path + "__" + AbstractProfiler.join_config(self.cfg)
        with open(self.prof_prop_adapted, "w") as text_file:
            text_file.write(output)

    def profile(self, iteration, stdout, stderr):
        print('creating profiling instance for Kieker Profiler')

        par_array = ['java',
                     self.java_agent,
                     self.skipDefaultAOP,
                     '-Dkieker.monitoring.configuration=' + self.prof_prop_adapted,
                     '-Dorg.aspectj.weaver.loadtime.configuration=file:' + os.path.join(self.project_root, self.aopXML),
                     '-jar',
                     self.executable]

        local_config = self.cfg
        self.command = AbstractProfiler.append_config_parameter(par_array, local_config)

        # TODO: in case of pmd the cli interface is not the same
        if self.software_name == 'pmd':
            print('Adapt shell script to weave kieker pmd')
            self.command = AbstractProfiler.append_config_parameter(['jars/pmd-bin-6.15.0/bin/run_kieker.sh', 'pmd',
                                                                     self.prof_prop_adapted], self.cffg)

        # TODO: in case of cpd the cli interface is not the same
        if self.software_name == 'cpd':
            print('Adapt shell script to weave kieker cpd')
            self.command = AbstractProfiler.append_config_parameter(['jars/cpd-bin-6.15.0/bin/run_kieker.sh', 'cpd',
                                                                     self.prof_prop_adapted], self.cfg)
            
        print('execute profile', self.command)
        subprocess.call(self.command, stdout=stdout, stderr=stderr)
        print('done profiling iteration', iteration)
