#!/bin/bash

# clear prev data
#rm -r output/*
# rm std*
#rm jip/properties/*_*

# run profiling
# 0 - catena
# 1 - h2
# 2 - sunflow
./turbo_boost.sh disable
# taskset 0x1 python3 profiling_script.py /samplingStrategies/catena/catena_config_feature_pbd_125_5.txt /home/max/monitoring/output/
taskset 0x1 python3 profiling_script.py /samplingStrategies/catena/catena_config_feature_pbd_125_5.txt /run/media/max/6650AF2E50AF0441/tmp/test_kieker/
# taskset 0x1 python3 profiling_script.py /samplingStrategies/sunflow/sunflow_config_feature_pbd_49_7.txt /run/media/max/6650AF2E50AF0441/tmp/test_kieker/