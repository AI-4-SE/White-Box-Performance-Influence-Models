#! /bin/bash

CONFIGURATION_FILE=$1
REPETITIONS=$2
PROFILER=$3
PARTITION=$4

# example call: ./start_slurm_run.sh
#   /media/raid/[name]/performance_measurement_scripts/src/monitoring/samplingStrategies/catena/catena_config_feature_pbd_125_5.txt
#   5
#   [None|jip|kieker|kieker_args]
#   planck

RESULT_AFTER_SLASH=${CONFIGURATION_FILE##*/}
PROJECT=${RESULT_AFTER_SLASH%%_*}

SAMPLING_PRE=${RESULT_AFTER_SLASH##*_config_}
SAMPLING=${SAMPLING_PRE%%.*}

CONFIGS=$(cat $CONFIGURATION_FILE | wc -l)
HERE=$(pwd)
JOBNAME="${PROJECT}_measurement"

mkdir -p logs

echo "$(date) Submitting job $JOBNAME to $PARTITION monitoring $CONFIGS configurations."

sbatch --array=1-$CONFIGS --partition=$PARTITION --job-name=$JOBNAME ./slurm_run.sh $CONFIGURATION_FILE $REPETITIONS $PROJECT $PROFILER $SAMPLING
