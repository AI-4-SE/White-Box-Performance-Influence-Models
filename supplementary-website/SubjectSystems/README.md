## Subject Systems

On this page we list the subject systems that we use to evaluate our approach. Each folder contains a submodule to the subject system's source repository and the feature model we used for sampling the configurations.

| System | Domain | LoC | &#124;F&#124; | &#124;C&#124; | &#124;C<sup>FW</sup>&#124; | &#124;C<sup>PW</sup>&#124; |
| :---      | :--- | ---: | ---: | ---: | ---: | ---: |
| Batik     | SVG rasterizer | 191,964 |  31 | 9.6&sdot;10<sup>4</sup>| 28 | 337 |
| Catena    | Password hashing | 3,986 |   12 |  1.0&sdot;10<sup>9</sup>| 875 | 2625 |
| cpd       | Copy-paste detector | 118,402 |     7 |  1.1&sdot;10<sup>4</sup>| 40 | 115 |
| dc        | Image density converter | 4,821 |    24 |  3.4&sdot;10<sup>6</sup>| 1600 | 9700 |
| H2        | Database | 250,983 |    16 | 6.5&sdot;10<sup>11</sup>| 375 | 2275 |
| Kanzi     | Data compression | 20,297 |  40 | 4.3&sdot;10<sup>3</sup>| 34 | 458 |
| pmd       | Source-code analyzer | 136,869 |    11 |  5.1&sdot;10<sup>2</sup>| 36 | 104 |
| Prevayler | Database | 9,067 |    12 |  1.3&sdot;10<sup>5</sup>| 250 | 400 |
| Sunflow   | Rendering engine | 168,740 |     6 |  5.4&sdot;10<sup>6</sup>| 125 | n/a |



### Feature Tracing
Here we show performance-relevant feature distributions at system-level and method-level.

### Profiled vs. Unprofiled
We identified different patter for the influence of the profiler on the subject systems.

#### Catena

![Profiler Influence Catena](profiled-vs-unprofiled/profiler_corellation_catena_t_2_pbd_49_7.png)

#### CPD

![Profiler Influence CPD](profiled-vs-unprofiled/profiler_corellation_cpd_t_2_pbd_49_7.png)

#### Density-Converter

![Profiler Influence Density-Converter](profiled-vs-unprofiled/profiler_corellation_density-converter_t_2_pbd_49_7.png)

#### Prevayler

![Profiler Influence Prevayler](profiled-vs-unprofiled/profiler_corellation_prevayler_t_2_pbd_49_7.png)

#### H2

![Profiler Influence H2](profiled-vs-unprofiled/profiler_corellation_h2_t_2_pbd_125_5.png)

#### Sunflow

![Profiler Influence Sunflow](profiled-vs-unprofiled/profiler_corellation_sunflow_feature_pbd_125_5.png)
