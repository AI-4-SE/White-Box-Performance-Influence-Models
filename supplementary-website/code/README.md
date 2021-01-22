## Reference Implementation
### White-Box Performance-Influence Modeling

We provide a reference implementation of our approach in the ```modeling``` folder. The [monitoringDataParser.py](modeling/monitoringDataParser.py) parses all performance measurements. The method execution time values get extracted into an [pandas dataframe](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.html). We provide these dataframes in the [experiment_data](../experiment_data/) folder. The file [createPIM.py](modeling/createPIM.py) learns a white-box performance-influence model per experiment. 

### Preformance Measurement

We run the performance measurements on a cluster of 27 computer. The jobs are distributed with the [SLURM Workload Manager](https://slurm.schedmd.com/overview.html). The file [start_slurm_run.sh](monitoring/start_slurm_run.sh) starts an experiment.
