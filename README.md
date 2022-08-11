# Shopping Cart API

A simple Java application that models a shopping cart.


It exposes an API that can be called to:

- list the items in the cart 
- add an item to the cart 
- remove an item from the cart


This application persists its data in a H2 database.

This is a Spring Boot application and requires Java 17 and maven (but a Maven wrapper is provided).

To build (on UN*X):

```
 ./mvnw clean package
```

To run:

```
java -jar target/shoppingcart-1.0.0-SNAPSHOT.jar
```