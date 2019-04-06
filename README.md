# Getting Started

# demo-hackernews-service

### Overview
This is a demo application of a spring boot backend API that consumes an external web service of hacker news on a scheduled basis, stores the collected information on a local mongoDb database, and exposes two REST endpoints to get the list of active news, and also another endpoint to deactivate a new by id. 

* The application does not uses any user and/or password in order to authenticate to the local mongoDb instance. But, if your local mongoBd instance is secured, then you should update the mongodb uri properly on **application.yaml**, under **/src/main/resources** folder.
* By default, a new database **hacker-news** will be created on your local mongoDb instance, as well as a collection called **HackerNew** inside the database.
* It is not necessary to run any initial script to perform the initial data feed on the **HackerNew** collection, since an automatically scheduled task runs when the application is starting. This task is scheduled to run every 60 minutes.    

### Running your service

```
$ mvn package
$ java -jar target/demo-hackernews-service-0.0.1-SNAPSHOT.jar
```
After that, your application should be up and running on [localhost:8080](http://localhost:8080/hackernews-service/api)

### Tech stack
This is a quick list of the technologies and dependencies used to implement this demo:
* Java, version 1.8
* MongoDb, version 3.6
* Apache Tomcat, version 9.0.16
* Spring Boot, version 2.1.3 RELEASE
    * Spring-boot-starter-data-mongodb, version 2.1.3 RELEASE
    * Spring-boot-starter-data-rest, version 2.1.3 RELEASE
        * Spring-boot-starter-web, version 2.1.3 RELEASE
        * Spring-boot-data-rest-webmvc, version 2.1.3 RELEASE
    * Spring-boot-starter-test, version 2.1.3 RELEASE
* Junit, version 4.12
* Mockito, version 2.23.4
* Lombok, version 1.18.6
* Hamcrest, version 1.3
* Swagger and Swagger-ui, version 2.6.0
* Awaitillity, version 3.1.2

### API documentation
As this demo application uses **Swagger** and **Swagger-ui,** you can easilly view and try the REST endpoints exposed by this backend API.

With the application up and running, you can go to the swagger-ui url in your web browser, by default:
* [Swagger-ui: hackernews-api](http://localhost:8080/hackernews-service/api/swagger-ui.html#)

The backend API exposes two endpoints:
* [GET /hacker-news](http://localhost:8080/hackernews-service/api/swagger-ui.html#!/hacker-new-controller/getAllActiveHackerNewsUsingGET)
* [DELETE /hacker-news/{hackerNewId}](http://localhost:8080/hackernews-service/api/swagger-ui.html#!/hacker-new-controller/deactivateHackeNewByIdUsingDELETE)
