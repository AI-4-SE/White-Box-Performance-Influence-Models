#!python3

import os
from abc import ABC, abstractmethod


class ConfigCollection(ABC):

    def __init__(self, name, name_anno):
        self.executable = name
        self.executable_annotations = name_anno

    @abstractmethod
    def parse(self, cfg):
        pass


class CpdConfig(ConfigCollection):

    def __init__(self):
        super().__init__('', '')
        self.config_dict = {}

    def parse(self, cfg):
        conf = []
        conf_decoded = cfg.split(' ')[1][1:-1]
        conf_decoded = conf_decoded.split('%;%')

        # static configuration options:

        conf.append('--files')
        conf.append('/media/hdd/max/data/spark/')

        conf.append('--language')
        conf.append('java')

        # dynamic configuration options:
        if 'skip_duplicate_files' in conf_decoded:
            conf.append('--skip-duplicate-files')

        if 'failOnViolation' in conf_decoded:
            conf.append('--failOnViolation')
            conf.append('false')

        if 'ignoreliterals' in conf_decoded:
            conf.append('--ignore-literals')

        if 'ignore_identifiers' in conf_decoded:
            conf.append('--ignore-identifiers')

        if 'ignore_annotations' in conf_decoded:
            conf.append('--ignore-annotations')

        if 'text' in conf_decoded:
            conf.append('--format')
            conf.append('text')

        if 'csv' in conf_decoded:
            conf.append('--format')
            conf.append('csv')

        if 'csv_with_linecount_per_file' in conf_decoded:
            conf.append('--format')
            conf.append('csv_with_linecount_per_file')

        if 'xml' in conf_decoded:
            conf.append('--format')
            conf.append('xml')

        if 'vs' in conf_decoded:
            conf.append('--format')
            conf.append('vs')

        # handling of numeric features:
        for el in conf_decoded:

            if el.startswith('minimum_tokens;'):
                conf.append('--minimum-tokens')
                conf.append(el[15:])

        return conf


class PmdConfig(ConfigCollection):

    def __init__(self):
        super().__init__('', '')
        self.config_dict = {}

    def parse(self, cfg):
        conf = []
        conf_decoded = cfg.split(' ')[1][1:-1]
        conf_decoded = conf_decoded.split('%;%')

        # static configuration options:

        conf.append('-d')
        conf.append('/media/hdd/max/data/spark/core/')

        conf.append('-R')
        conf.append('softwareConfs/quickstart.xml')

        # dynamic configuration options:
        if 'cache' in conf_decoded:
            conf.append('-cache')
            conf.append('/media/hdd/max/data/cache.tmp')

        if 'shortnames' in conf_decoded:
            conf.append('-shortnames')

        if 'showsuppressed' in conf_decoded:
            conf.append('-showsuppressed')

        if 'codeclimate' in conf_decoded:
            conf.append('-format')
            conf.append('codeclimate')

        if 'summaryhtml' in conf_decoded:
            conf.append('-format')
            conf.append('summaryhtml')

        elif 'html' in conf_decoded:
            conf.append('-format')
            conf.append('html')

        if 'emacs' in conf_decoded:
            conf.append('-format')
            conf.append('emacs')

        if 'csv' in conf_decoded:
            conf.append('-format')
            conf.append('csv')

        if 'no_cache' in conf_decoded:
            conf.append('-no-cache')

        # handling of numeric features:
        for el in conf_decoded:

            if el.startswith('threads;'):
                conf.append('-threads')
                conf.append(el[8:])

        return conf


class DensityConverterConfig(ConfigCollection):

    def __init__(self):
        super().__init__('dconvert.jar', 'dconvert_a.jar')
        self.config_dict = {}

    def parse(self, cfg):
        conf = []
        conf_decoded = cfg.split(' ')[1][1:-1]
        conf_decoded = conf_decoded.split('%;%')

        # static configuration options:

        conf.append('-src')
        conf.append('/media/raid/max/data/fosd1029/')

        conf.append('-dst')
        conf.append('/media/hdd/max/data_output/')

        # dynamic configuration options:
        if 'all' in conf_decoded:
            conf.append('-platform')
            conf.append('all')

        if 'android' in conf_decoded:
            conf.append('-platform')
            conf.append('android')

        if 'win' in conf_decoded:
            conf.append('-platform')
            conf.append('win')

        if 'web' in conf_decoded:
            conf.append('-platform')
            conf.append('web')

        if 'ios' in conf_decoded:
            conf.append('-platform')
            conf.append('ios')

        if 'androidIncludeLdpiTvdpi' in conf_decoded:
            conf.append('-androidIncludeLdpiTvdpi')

        if 'androidMipmapInsteadOfDrawable' in conf_decoded:
            conf.append('-androidMipmapInsteadOfDrawable')

        if 'antiAliasing' in conf_decoded:
            conf.append('-antiAliasing')

        if 'iosCreateImagesetFolders' in conf_decoded:
            conf.append('-iosCreateImagesetFolders')

        if 'keepOriginalPostProcessedFiles' in conf_decoded:
            conf.append('-keepOriginalPostProcessedFiles')

        if 'png' in conf_decoded:
            conf.append('-outCompression')
            conf.append('png')

        if 'bmp' in conf_decoded:
            conf.append('-outCompression')
            conf.append('bmp')

        if 'gif' in conf_decoded:
            conf.append('-outCompression')
            conf.append('gif')

        if 'jpg' in conf_decoded:
            conf.append('-outCompression')
            conf.append('jpg')

        if 'round' in conf_decoded:
            conf.append('-roundingMode')
            conf.append('round')

        if 'ceil' in conf_decoded:
            conf.append('-roundingMode')
            conf.append('ceil')

        if 'floor' in conf_decoded:
            conf.append('-roundingMode')
            conf.append('floor')

        if 'skipExisting' in conf_decoded:
            conf.append('-skipExisting')

        # handling of numeric features:
        for el in conf_decoded:
            # TODO: in case of density converter the compression quality has to be in the range between 0.0 and 1.0
            # TODO: splc can only work with integer numbers as feature values so: 0-10 -> 0.0-1.0
            if el.startswith('compressionQuality;'):
                conf.append('-compressionQuality')
                conf.append(str(float(el[19:])/10))

            if el.startswith('scale;'):
                conf.append('-scale')
                conf.append(el[6:])

            if el.startswith('threads;'):
                conf.append('-threads')
                conf.append(el[8:])

        return conf


class PrevaylerConfig(ConfigCollection):

    def __init__(self):
        super().__init__('prevayler.jar', 'prevayler_a.jar')
        self.config_dict = {}

    def parse(self, cfg):
        conf_decoded = cfg.split(' ')[1][1:-1]
        conf_decoded = conf_decoded.split('%;%')
        # conf_decoded = conf_decoded.split('%;%')

        # prevayler config options are an special case because they are propagated through a config file
        if 'RunPrevaylerQueryTest' in conf_decoded:
            self.config_dict['RunPrevaylerQueryTest'] = 'YES'
        else:
            self.config_dict['RunPrevaylerQueryTest'] = 'NO'

        if 'RunPrevaylerTransactionTest' in conf_decoded:
            self.config_dict['RunPrevaylerTransactionTest'] = 'YES'
        else:
            self.config_dict['RunPrevaylerTransactionTest'] = 'NO'

        if 'TransactionTestCheckConsistency' in conf_decoded:
            self.config_dict['TransactionTestCheckConsistency'] = 'YES'
        else:
            self.config_dict['TransactionTestCheckConsistency'] = 'NO'

        if 'ONE_MILLION' in conf_decoded:
            self.config_dict['NumberOfObjects'] = 'ONE_MILLION'
        else:
            self.config_dict['NumberOfObjects'] = 'ONE_HUNDRED_THOUSAND'

        # handling of numeric features:
        for el in conf_decoded:
            if el.startswith('PrevaylerQueryThreadsMinimum;'):
                self.config_dict['PrevaylerQueryThreadsMinimum'] = el[29:]

            if el.startswith('PrevaylerQueryThreadsMaximum;'):
                self.config_dict['PrevaylerQueryThreadsMaximum'] = el[29:]

            if el.startswith('PrevaylerTransactionThreadsMinimum;'):
                self.config_dict['PrevaylerTransactionThreadsMinimum'] = el[35:]

            if el.startswith('PrevaylerTransactionThreadsMaximum;'):
                self.config_dict['PrevaylerTransactionThreadsMaximum'] = el[35:]

        return self.config_dict


class CatenaConfig(ConfigCollection):

    def __init__(self):
        super().__init__('catena.jar', 'catena_2020.jar')
        self.wl = ['Butterfly-Full-adapted', '6789ab', '6789ab', '6789ab', '64']

    def parse(self, cfg):
        conf = []

        conf_decoded = cfg.split(' ')[1][1:-1]
        conf_decoded = conf_decoded.split('%;%')

        if 'blake2b' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'gamma' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        # 1-DBG, 2-BRG, 3-GRG, 4-SBRG
        if 'dbg' in conf_decoded:
            conf.append('1')
        elif 'sbrg' in conf_decoded:
            conf.append('4')
        elif 'grg' in conf_decoded:
            conf.append('3')
        elif 'brg' in conf_decoded:
            conf.append('2')

        if 'phi' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        for el in conf_decoded:
            if el.startswith('garlic;'):
                conf.append(el[7:])
            if el.startswith('lambda;'):
                conf.append(el[7:])
            if el.startswith('v_id;'):
                conf.append(el[5:])
            if el.startswith('d;'):
                conf.append(el[2:])

        return conf+self.wl


class H2Config(ConfigCollection):

    def __init__(self):
        super().__init__('h2.jar', 'h2_a.jar')
        self.wl_h2 = ['100000']

    def parse(self, line):
        conf = []
        conf_decoded = line.split(' ')[1][1:-1]
        # print(conf_decoded)
        conf_decoded = conf_decoded.split('%;%')
        # print(conf_decoded)
        # print(conf)
        for el in conf_decoded:
            if el.startswith('analyze_auto;'):
                # print(el[13:])
                conf.append(el[13:])
            if el.startswith('analyze_sample;'):
                conf.append(el[15:])
                # print(el[15:])
        # print(conf)
        if 'COMPRESS' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'EARLY_FILTER' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'MULTI_THREADED' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'MV_STORE' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_EVALUATABLE_SUBQUERIES' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_IN_LIST' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_IN_SELECT' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_INSERT_FROM_SELECT' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_IS_NULL' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_OR' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'OPTIMIZE_TWO_EQUALS' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'PAGE_STORE_INTERNAL_COUNT' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'RECOMPILE_ALWAYS' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        if 'ROWID' in conf_decoded:
            conf.append('1')
        else:
            conf.append('0')

        # print(conf)
        conf = conf + self.wl_h2
        # print(conf)
        return conf


class SunflowConfig(ConfigCollection):

    def __init__(self):
        super().__init__('sunflow.jar', 'sunflow_2020.jar')
        self.wl = ['128']

    def parse(self, line):
        conf = []

        # print(line)
        conf_decoded = line.split(' ')[1][1:-1]
        # print(conf_decoded)
        # print(conf_decoded.split('%;%'))

        conf_decoded = conf_decoded.split('%;%')
        # print(conf_decoded)

        for el in conf_decoded:
            if el.startswith('threads;'):
                conf.append(el[8:])
            if el.startswith('diffuseDepth;'):
                conf.append(el[13:])
            if el.startswith('reflectionDepth;'):
                conf.append(el[16:])
            if el.startswith('refractionDepth;'):
                conf.append(el[16:])
            if el.startswith('bucketSize;'):
                conf.append(el[11:])
            if el.startswith('samples;'):
                conf.append(el[8:])

        return conf+self.wl


class FakerConfig(ConfigCollection):

    def __init__(self):
        super().__init__('faker', None)
        # no wl adjustable
        self.wl = ''

    def parse(self, line):
        conf = []
        first = True

        conf_decoded = line.split(' ')[1][1:-1]
        conf_decoded = conf_decoded.split('%;%')

        for el in conf_decoded:
            if el.startswith('repeat;'):
                conf.append('-r='+el[7:])

        output_folder_cprofile = '/tmp/faker_output/'
        if not os.path.exists(output_folder_cprofile):
            os.makedirs(output_folder_cprofile)
        num_files = len(os.listdir(output_folder_cprofile))

        if 'output' in conf_decoded:
            string = output_folder_cprofile + str(num_files)+'_faker.out'
            conf.append('-o')
            conf.append(string)

        if 'separator' in conf_decoded:
            conf.append("-s=';'")

        if 'de_DE' in conf_decoded:
            conf.append('-l de_DE')
        if 'hi_IN' in conf_decoded:
            conf.append('-l hi_IN')
        if 'ko_KR' in conf_decoded:
            conf.append('-l ko_KR')
        if 'ru_RU' in conf_decoded:
            conf.append('-l ru_RU')
        if 'zh_TW' in conf_decoded:
            conf.append('-l zh_TW')

        if 'fake' in conf_decoded:
            if 'address_fake' in conf_decoded:
                conf.append('address')
            elif 'text' in conf_decoded:
                conf.append('text')
            else:
                # default case if there is no option for fake encoded
                conf.append('name')

        if 'profile' in conf_decoded:
            conf.append('profile')

        if 'job' in conf_decoded:
            if first:
                conf.append('job')
                first = False
            else:
                conf[-1] += ',job'
        if 'company' in conf_decoded:
            if first:
                conf.append('company')
                first = False
            else:
                conf[-1] += ',company'
        if 'ssn' in conf_decoded:
            if first:
                conf.append('ssn')
                first = False
            else:
                conf[-1] += ',ssn'
        if 'residence' in conf_decoded:
            if first:
                conf.append('residence')
                first = False
            else:
                conf[-1] += ',residence'
        if 'current_location' in conf_decoded:
            if first:
                conf.append('current_location')
                first = False
            else:
                conf[-1] += ',current_location'
        if 'blood_group' in conf_decoded:
            if first:
                conf.append('blood_group')
                first = False
            else:
                conf[-1] += ',blood_group'
        if 'website' in conf_decoded:
            if first:
                conf.append('website')
                first = False
            else:
                conf[-1] += ',website'
        if 'username' in conf_decoded:
            if first:
                conf.append('username')
                first = False
            else:
                conf[-1] += ',username'
        if 'name' in conf_decoded:
            if first:
                conf.append('name')
                first = False
            else:
                conf[-1] += ',name'
        if 'sex' in conf_decoded:
            if first:
                conf.append('sex')
                first = False
            else:
                conf[-1] += ',sex'
        if 'address' in conf_decoded:
            if first:
                conf.append('address')
                first = False
            else:
                conf[-1] += ',address'
        if 'mail' in conf_decoded:
            if first:
                conf.append('mail')
                first = False
            else:
                conf[-1] += ',mail'
        if 'birthdate' in conf_decoded:
            if first:
                conf.append('birthdate')
            else:
                conf[-1] += ',birthdate'

        # localized_provider is mandatory -> if no language is defined, no need to define a locale
        # -separator
        # -de_DE -hi_IN -ko_KR -ru_RU -zh_TW
        # -fake -name_fake -address_fake -text
        # -profile: job company ssn residence current_location blood_group website username name_
        #           profile sex address_profile mail birthdate

        # print(conf)
        return conf
