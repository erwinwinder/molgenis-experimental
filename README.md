molgenis-experimental
=====================

Implementation based on: 

* Dropwizard
* Guice
* EclipseLink
* Postgresql
* (Elasticsearch)

To Run:

* Install postgresql
* Run mvn clean install
* java -jar target/molgenis-database-2.0-SNAPSHOT.jar server develop.yml

From eclipse:

* Run MolgenisApplication.java with program arguments: server develop.yml

Project running on port 8080