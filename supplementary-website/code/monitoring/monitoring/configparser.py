#!python3

from configcollection import CatenaConfig, H2Config, SunflowConfig, FakerConfig, PrevaylerConfig, \
    DensityConverterConfig, PmdConfig, CpdConfig


class ConfigParser:

    def __init__(self, project_name):

        if project_name == 'catena':
            self.software_system = CatenaConfig()

        elif project_name == 'h2':
            self.software_system = H2Config()

        elif project_name == 'sunflow':
            self.software_system = SunflowConfig()

        elif project_name == 'faker':
            self.software_system = FakerConfig()

        elif project_name == 'prevayler':
            self.software_system = PrevaylerConfig()

        elif project_name == 'density-converter':
            self.software_system = DensityConverterConfig()

        elif project_name == 'pmd':
            self.software_system = PmdConfig()

        elif project_name == 'cpd':
            self.software_system = CpdConfig()

    def parse(self, cfg):
        return self.software_system.parse(cfg)
