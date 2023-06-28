# Evolutionary Algorithm

An Evolutionary Algorithm Library

Carlos Cotta, 2023

## Requirements

Requires JDK 17 or higher. The project relies on the [json-simple](https://cliftonlabs.github.io/json-simple/) library for parsing configuration files and dumping statistics. A Maven dependency is included in the `pom.xml` file for this purpose. Alternatively, non-Maven users may download [`json-simple-4.0.1.jar`](https://cliftonlabs.github.io/json-simple/target/json-simple-4.0.1.jar) and add it to the project build path.

## Usage 

See `test/TestEA` class for an example of use. 

The configuration of the EA is done via a JSON file (see `run/bitstring.json` for an example).

If you are using Maven, the following dependency can be added to your project:

~~~
    <dependency>
    	<groupId>es.uma.lcc.caesium</groupId>
    	<artifactId>ea</artifactId>
  	<version>1.0</version>
    </dependency>
~~~

## Applications

The library has been used in the following projects (non-exhaustive list):

* [ea-model-byzantine](https://github.com/Bio4Res/ea-model-byzantine): Evolutionary algorithms with Byzantine failures in fitness evaluation 

