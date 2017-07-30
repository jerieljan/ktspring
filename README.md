# KTSpring

A Spring + Kotlin Reference Project

If you want to start on a Spring Boot project while using Kotlin, 
this project helps you get to do that even easier.

## Quick Start

Simply load the `ktspring.iml` or load the `build.gradle` file on your 
IDE.

This is a **Spring Boot** project running **Gradle**.

    gradle bootRun

### MongoDB

You can easily start a Mongo instance using *Docker*.

    docker run --name mongo-ktspring -d mongo

Once downloaded, you can run its shell via
    
    docker exec -it mongo-ktspring mongo
    
*Docker* isn't essential, but is highly recommended since it keeps these database
setups neat and tidy under a single container that you can freely run and destroy
at ease.
    
...if not, well, you can just follow the MongoDB installation process on your machine.

