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

    elif sub_sys == 'batik':
        x_train = [parse_config_batik(x) for x in x_train]
        x_test = [parse_config_batik(x) for x in x_test]

    elif sub_sys == 'kanzi':
        x_train = [parse_config_kanzi(x) for x in x_train]
        x_test = [parse_config_kanzi(x) for x in x_test]

    else:
        print('no config transformation defined for', sub_sys)
        raise Exception('No config parser defined')
    return x_train, x_test


def transform_config_single(sub_sys, x_train):
    # copied from Notebook "Coarse Grained PIM"
    if sub_sys == 'catena':
        x_train = [parse_config_catena(x) for x in x_train]
        x_train = [catena_config_transformation(x_tr) for x_tr in x_train]

    elif sub_sys == 'h2':
        x_train = [parse_config_h2(x) for x in x_train]

    elif sub_sys == 'sunflow':
        x_train = [parse_config_sunflow(x) for x in x_train]

    elif sub_sys == 'density-converter':
        x_train = [parse_config_density(x) for x in x_train]

    elif sub_sys == 'pmd':
        x_train = [parse_config_pmd(x) for x in x_train]

    elif sub_sys == 'cpd':
        x_train = [parse_config_cpd(x) for x in x_train]

    elif sub_sys == 'prevayler':
        x_train = [parse_config_prevayler(x) for x in x_train]

    elif sub_sys == 'batik':
        x_train = [parse_config_batik(x) for x in x_train]

    elif sub_sys == 'kanzi':
        x_train = [parse_config_kanzi(x) for x in x_train]

    else:
        print('no config transformation defined for', sub_sys)
        raise Exception('No config parser defined')
    return x_train

def parse_config_kanzi(cfg):

    out = []
    # compressionLevel | ( entropyCodec + transformCodec)
    # number options: 9 | ( 8 + 9 ) = 26 Features
    # entropyCodec: NoneEntropy, Huffman, ANS0, ANS1, Range, FPAQ, TPAQ, CM
    # transformCodec: NoneTransform, BWTS, ROLZ, RLT, ZRLT, MTFT, RANK, TEXT, X86

    if 'compressionLevel%;%0' in cfg:
        out += [1, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%1' in cfg:
        out += [0, 1, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%2' in cfg:
        out += [0, 0, 1, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%3' in cfg:
        out += [0, 0, 0, 1, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%4' in cfg:
        out += [0, 0, 0, 0, 1, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%5' in cfg:
        out += [0, 0, 0, 0, 0, 1, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%6' in cfg:
        out += [0, 0, 0, 0, 0, 0, 1, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%7' in cfg:
        out += [0, 0, 0, 0, 0, 0, 0, 1, 0,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]
    elif 'compressionLevel%;%8' in cfg:
        out += [0, 0, 0, 0, 0, 0, 0, 0, 1,   0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0]

    else:
        out += [0, 0, 0, 0, 0, 0, 0, 0, 0]

        if 'NoneEntropy' in cfg:
            out += [1, 0, 0, 0, 0, 0, 0, 0]
        elif 'Huffman' in cfg:
            out += [0, 1, 0, 0, 0, 0, 0, 0]
        elif 'ANS0' in cfg:
            out += [0, 0, 1, 0, 0, 0, 0, 0]
        elif 'ANS1' in cfg:
            out += [0, 0, 0, 1, 0, 0, 0, 0]
        elif 'Range' in cfg:
            out += [0, 0, 0, 0, 1, 0, 0, 0]
        elif 'FPAQ' in cfg:
            out += [0, 0, 0, 0, 0, 1, 0, 0]
        elif 'TPAQ' in cfg:
            out += [0, 0, 0, 0, 0, 0, 1, 0]
        elif 'CM' in cfg:
            out += [0, 0, 0, 0, 0, 0, 0, 1]
        else:
            out += [0, 0, 0, 0, 0, 0, 0, 0]


        if 'NoneTransform' in cfg:
            out += [1, 0, 0, 0, 0, 0, 0, 0, 0]
        elif 'BWTS' in cfg:
            out += [0, 1, 0, 0, 0, 0, 0, 0, 0]
        elif 'ROLZ' in cfg:
            out += [0, 0, 1, 0, 0, 0, 0, 0, 0]
        elif 'RLT' in cfg:
            out += [0, 0, 0, 1, 0, 0, 0, 0, 0]
        elif 'ZRLT' in cfg:
            out += [0, 0, 0, 0, 1, 0, 0, 0, 0]
        elif 'MTFT' in cfg:
            out += [0, 0, 0, 0, 0, 1, 0, 0, 0]
        elif 'RANK' in cfg:
            out += [0, 0, 0, 0, 0, 0, 1, 0, 0]
        elif 'TEXT' in cfg:
            out += [0, 0, 0, 0, 0, 0, 0, 1, 0]
        elif 'X86' in cfg:
            out += [0, 0, 0, 0, 0, 0, 0, 0, 1]
        else:
            out += [0, 0, 0, 0, 0, 0, 0, 0, 0]


    if '1k' in cfg:
        out += [1000]
    elif '1m' in cfg:
        out += [1000000]
    elif '1g' in cfg:
        out += [1000000000]

    if 'checksum' in cfg:
        out += [1]
    else:
        out += [0]

    if 'skip' in cfg:
        out += [1]
    else:
        out += [0]

    if '1job' in cfg:
        out += [1]
    elif '2job' in cfg:
        out += [2]
    elif '3job' in cfg:
        out += [3]
    elif '4job' in cfg:
        out += [4]

    return [int(val) for val in out]

def parse_config_batik(cfg):

    out = []
    if 'image_png' in cfg:
        out += [1, 0, 0, 0]
    elif 'image_jpeg' in cfg:
        out += [0, 1, 0, 0]
    elif 'image_jpe' in cfg:
        out += [0, 0, 1, 0]
    elif 'application_pdf' in cfg:
        out += [0, 0, 0, 1]

    if 'q001' in cfg:
        out+=[0.01]
    elif 'q05' in  cfg:
        out+=[0.5]
    elif 'q099' in  cfg:
        out+=[0.99]

    if 'indexed1' in cfg:
        out+=[1]
    elif 'indexed2' in cfg:
        out+=[2]
    elif 'indexed4' in cfg:
        out+=[4]
    elif 'indexed8' in cfg:
        out+=[8]

    if 'onload' in cfg:
        out+=[1]
    else:
        out+=[0]

    if 'scriptSecurityOff' in cfg:
        out+=[1]
    else:
        out+=[0]

    if 'bg' in cfg:
        out+=[1]
    else:
        out+=[0]

    if 'w200' in cfg:
        out += [200]
    elif 'w400' in cfg:
        out += [400]
    elif 'w600' in cfg:
        out += [600]
    elif 'w800' in cfg:
        out += [800]
    elif 'w1000' in cfg:
        out += [1000]

    if 'h200' in cfg:
        out += [200]
    elif 'h400' in cfg:
        out += [400]
    elif 'h600' in cfg:
        out += [600]
    elif 'h800' in cfg:
        out += [800]
    elif 'h1000' in cfg:
        out += [1000]

    if 'dpi50' in cfg:
        out+=[50]
    elif 'dpi100'in cfg:
        out+=[100]
    elif 'dpi150'in cfg:
        out+=[150]
    elif 'dpi200'in cfg:
        out+=[200]
    elif 'dpi250'in cfg:
        out+=[250]
    elif 'dpi300'in cfg:
        out+=[300]

    return [int(val) for val in out]

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

    return [int(val) for val in out]


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
    out += [int(numbers[1])]

    return out


def parse_config_cpd(cfg):
    # cfg--files_-media-hdd-max-data-spark-_--language_java_--ignore-identifiers_--ignore-annotations_--format_csv_--minimum-tokens_590
    # [0, 0, 0, 1, 1, 0, 1, 0, 0, 0, '590']
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
    out += [int(vals[1])]

    return out


def parse_config_density(cfg):
    # example:
    # [0, 0, 0, 1, 0 ,  0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, '0.2', '33', '4']
    # cfg-src_-media-raid-max-data-fosd1029-_-dst_-media-hdd-max-data_output-_
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
    out += [float(numbers[1]), int(numbers[3]), int(numbers[5])]
    return out


def parse_config_h2(cfg):
    # 2001_5001_0_0_0_0_1_0_0_0_0_0_0_0_0_0_50000
    # [2001, 5001, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    return [int(val) for val in cfg.split('_')[:15]]


def parse_config_sunflow(cfg):
    # 1_21_0_0_0_0_128
    # [1, 21, 0, 0, 0, 0, 128]
    return [int(val) for val in cfg.split('_')[:5]]


def parse_config_catena(cfg):
    # cfg =  1_0_1_0_10_49_0_64_Butterfly-Full-adapted_6789ab_6789ab_6789ab_64
    # out = [1 0 1 0 10 49 0 64]
    return [int(val) for val in cfg.split('_')[:8]]


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
