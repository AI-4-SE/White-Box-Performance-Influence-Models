#!python3

from profiling.profilerjip import ProfilerJIP
from profiling.profilerNone import ProfilerNone
from profiling.profilerKiekerArgs import ProfilerKiekerArgs
from profiling.profilerjProfiler import ProfilerjProfiler


class ProfilerFactory:

    impl_prof = ['None', 'jip', 'kiekerArgs', 'cprofile', 'jProfiler']

    def __init__(self, profiler_name, executable, software_name, sampling_strategy, cfg):

        if profiler_name == ProfilerFactory.impl_prof[0]:
            self.profiler = ProfilerNone(executable, software_name, sampling_strategy, cfg)

        elif profiler_name == ProfilerFactory.impl_prof[1]:
            self.profiler = ProfilerJIP(executable, software_name, sampling_strategy, cfg)

        elif profiler_name == ProfilerFactory.impl_prof[2]:
            self.profiler = ProfilerKiekerArgs(executable, software_name, sampling_strategy, cfg)

        elif profiler_name == ProfilerFactory.impl_prof[3]:
            pass

        elif profiler_name == ProfilerFactory.impl_prof[4]:
            self.profiler = ProfilerjProfiler(executable, software_name, sampling_strategy, cfg)

        else:
            print('use one of the following profiler:', ProfilerFactory.impl_prof)

    def execute(self, rep):
        return self.profiler.execute_profiler(rep)
