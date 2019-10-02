# Item Repository Spring Boot Application #

### Project Overview ###

This project contains an unfinished implementation of a simple repository for Items.


### Application structure ###

The application is implemented as a Spring Boot application using an in memory H2 Database Engine to store the data. 

The application is structured in a controller package with an ItemController that exposes a REST endpoint with one GET Items method. A model package with the data model. This data model currently only contains one entity Item. A repository package with an ItemRepository to access the database.

The main class in the application is com.cepheid.cloud.skel.SkelApplication. Running this class will start the application and insert four empty Item entities into the database.

There is also one unfinished JUnit test in com.cepheid.cloud.skel.ItemControllerTest

### Task ###

Your task is to "complete" the implementation. At a very minimum you need to implement the following:

* Add some attributes to the Item class, e.g. a name, a state that can have one of these three values (undefined, valid, invalid) etc.
* Create a new entity class Description, and setup a one to many relation between Item and Description. I.e. an Item has many Descriptions.
* Add Item CRUD methods to the controller.
* Add methods to the controller to query for specific Items based on it's attributes.
* Add something of your own choice that uses something from the Spring framework or demonstrates some Object-Oriented Programming Concepts. 

Whatever code you produce, at the end of this exercise it should be of production quality, compile and run even if functionality is not complete.

### Installation ###

Clone or download the repository from https://github.com/rxdevcepheid/spring-skel

The project is prepared to be used with Gradle and Eclipse IDE. (Gradle plugin buildship is by default included in Eclipse). You can of course use any other IDE if you like.

Project is configured to use JavaSE-11. But it works with JavaSE-1.8 or greater, so you can either install Java 11 or change the project settings to your existing version of Java.

Project is tested using Eclipse 2018-12 (seems to be some problem when using Eclipse 2019). https://www.eclipse.org/eclipseide/2018-12/

This is how you import the project into Eclipse:

1. Choose File->Import..., select Existing Gradle Project.
1. Set Project root directory to where you cloned the repository.
1. Import Options, choose Gradle Wrapper.


### Starting application/ Run Tests ###

Right click on com.cepheid.cloud.skel.SkelApplication and select Run As -> Java Application
Right click on com.cepheid.cloud.skel.ItemControllerTest and select Run As -> JUnit Test

### Useful Links ###

* https://spring.io/projects/spring-boot
* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
* https://www.h2database.com/html/main.html
