# Catena Java
Implementation of the Catena password scrambling framework in Java.

## Submodules
For the tests the test vectors from the [Catena Test Vectors
Repository](https://github.com/medsec/catena-test-vectors), which are
included as a git submodule, are required. To clone this repository with all
submodules run

    $ git clone --recursive https://github.com/medsec/catena-java.git

## Building
To build the project with maven, run:

    $ mvn package

This also runs all tests.

## Documentation
To build the documentation, run:

    $ mvn site

The generated documentation can be found at
`./catena/target/site/apidocs/index.html`.

## Code Coverage
After the library was build, the code coverage report can be found at
`./catena/target/jacoco-ut`.
