#! /bin/bash

#SBATCH --output=/media/raid/max/performance_measurement_scripts/monitoring/logs/%x_%a.out
#SBATCH --error=/media/raid/max/performance_measurement_scripts/monitoring/logs/%x_%a.err

CONFIGURATION_FILE=$1
REPETITIONS=$2
PROJECT=$3
PROFILER=$4
SAMPLING=$5

echo $HOSTNAME

TASK_ID=$SLURM_ARRAY_TASK_ID
CONFIGURATION=$(sed "${TASK_ID}q;d" $CONFIGURATION_FILE)

taskset 0x1 python3 monitoring/profiling_script.py "$CONFIGURATION" "$PROJECT" "$SAMPLING" "$PROFILER" $REPETITIONS
