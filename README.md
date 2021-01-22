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



## Data

## Programs and Scripts

## Installation

## Usage
