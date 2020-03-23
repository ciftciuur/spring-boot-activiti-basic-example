## Create Simple Business Process Demo

# How to run this example

after the project is clone

create postgres user :postgres pw:postgres or config changed you postgres user
 
import maven project -> maven clean and install -> ProcessApplication right click run
 
$ mvn clean package spring-boot:repackage
 
$ java -jar target/spring-boot-activiti-basic-example.jar (note : You need to create database named "process_db")
