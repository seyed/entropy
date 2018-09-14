# entropy

This repository holds an application that calculates the location entropy of a given data. It also holds its related unit tests with the code coverage of 96.88%. 

Used Language/Framework/Plugin
-------------------------

- Scala: For both implementation and tests.  
- Apache Spark: To transform the data into dataset and dataframes for required calculation. 
- ScalaTest: Used framework to write the unit tests. 
- Scoverage: To check the coverage of the written tests. 


Real World Data
---------------
The data used here is of type `*.geojson` and taken from this url: https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson


How to test the application (Including coverage)
---------------------------
Run the following command: 
- `sbt clean test`

For checking the coverage:

1. Run `sbt coverage test`
2. Then, run `sbt coverageReport`
3. See the report from `target/scala-2.11/scoverage-report/index.html`


How to run the application
--------------------------
1. First package the application by running `sbt package`
2. Move the real world data (`earthquake-record.geojson`) into the `target/scala-2.11` folder. 
3. Use the `spark-submit` and run this command: `spark-submit --class "App" --master local[4] location-entropy-calculator_2.11-0.1.0-SNAPSHOT.jar`
4. See the result from the created file `calculated-location-entropy.txt`.

