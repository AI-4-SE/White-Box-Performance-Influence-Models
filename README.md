[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)


# White-Box Performance-Influence Models

White-box performance-influence models is an approach for modeling configuration-dependent software properties (like performance) at the method level. With these models one can predict the performance of methods given the configuration on a software system.

The approach consists of two steps: First, a coarse-grained profiler (jProfiler) is used to measure the performance of each method that gets executed. This is done for different configurations. Performance-influence models are then used to learn a model for each method.

The second step then focuses measurement and analyse on a subset of methods. Here, an other profiler (Kieker) is used to measure all induvidual method executions, which may result in terrabytes of method execution data, depending on the number of method executions.

## Overview

![Sketch](WhiteBoxWorkflow-Page-2.png)

We give an overview of our approach in the figure above.

**Configurable Systems + Sampling**
The process starts by selecting a software system to use. Next, a set of configurations is selected by a sampling strategie. The folder [Subject Systems](supplementary-website/SubjectSystems) containes the binyries and used sets of configurations for the subject systems.

**Profiling (coarse-grained) + Profiling (fine-grained)**
Profiling is done by executing the software systems while collecting performance data via a profiler. Each configuration gets executed individually with a new instance of the JVM. We used [jProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html) as the coarse-grained profiler, getting the measured execution time of each individual method of the subject system. We used [Kieker](http://kieker-monitoring.net/), an instrumentation-based profiler, for measuring the execution time for each individual method execution of the set of instrumented methods. The instrumentation is done manually. This profling step may results in large ammounts of data. Therefore, we compressed the results of each program execution during measurement to be able to save the data.

**Filter Methods + Filter Outlier**
The abstractprofiler.py in the monitoring/monitoring/profiling/ folder


## Data

Each file in the ```supplementary-website/data/``` folder contains the measurement data of one experiment. Each experiment is defined by the subject system, the sampling strategie, and the profiler. In order to reduce file size, we compressed the experiment files with [gzip](https://wiki.ubuntuusers.de/gzip/).

Files conteining ```*__rnd100__*``` belong to the test set of the corresponding subject systems.


## Installation

requirements.txt


## Programs and Scripts

**Profiling a System**

For profiling, it is neccessary to have a slurm cluster running. To set up the slurm workload manager we refer to the official guides and tutorials: [slurm](https://slurm.schedmd.com/overview.html). It is neccessary to have at least some experience with slurm to run these measurement scripts.
We provide all neccessary scripts for profiling a softwaresystem in the ```supplementary-website/code/monitoring/``` directory. The output path has to be defined on the file `supplementary-website/code/monitoring/monitoring/profiling/abstractprofiler.py` as `self.base_output_path = '/tmp/'`. This has to be done only once for the slurm cluster. The file ```supplementary-website/code/monitoring/start_slurm_run.sh``` serves as the startscript that is executed in the slurm master. The interface is:
```
./start_slurm_run.sh <path/to/configurations> <number repetitions> <profiler> <slurm partition>
./start_slurm_run.sh samplingStrategies/catena/catena_config_feature_pbd_125_5.txt 5 None planck
```


**Generating Method-Level Models**

## Usage

**Profiling a System**



**Generating Method-Level Models**

modeling:
1. extract data with [gzip](https://wiki.ubuntuusers.de/gzip/)
2. ```learn_method_level_model.py <in_file> <eval_file> <output_path>```
