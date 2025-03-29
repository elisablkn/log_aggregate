# Distributed Log Aggregate

A distrubuted log aggregation service that collects and queries logs from different microservices.

#### Architecture and Implementation Design

To complete the task, I decided to follow the Model-View-Controller pattern using the Java SpringBoot framework. 

#### 1. Model (`src/main/java/com/sonatus/intern`)

`Log.java` represents a single log that has fields `service_name`, `timestamp`, and `messsage` that follow the given formats. 
`Response.java` represents a single log message with a timestamp that the GET request is expected to return.

#### 2. Service (`src/main/java/com/sonatus/intern`)

`LogsService.java` handles storing and retrieving logs. To make the API thread-safe and support concurrent requests, I integrated the [Caffeine](https://github.com/ben-manes/caffeine) library, which provides an in-memory cache with built-in concurrency support and automatic eviction. In this application, logs are configured to expire after 1 hour.

#### 3. Controller (`src/main/java/com/sonatus/intern`)

`InternController.java` defines the RESTful API endpoints, supporting POST and GET requests in predefined formats.


## Running the project

Clone this repo

```bash
git clone https://github.com/elisablkn/log_aggregate.git
cd log_aggregate
```

Have Java (the project uses Java 21) and Maven installed. 

Inside the root directory of the project run

```bash
mvn clean install
mvn spring-boot:run
```
The API is now running on port `8080`. Access it through `http://localhost:8080/logs` in browser or with `cURL`.

Example of a POST `cURL` command:
```bash
curl -X POST "http://localhost:8080/logs" -H "Content-Type: application/json" -d '{"service_name":"auth-service","timestamp":"2025-03-17T10:00:05Z","message":"User login successful"}'
```

Example of a GET `cURL` command:

```bash
curl "http://localhost:8080/logs?service=auth-service&start=2025-03-17T10:00:00Z&end=2025-03-17T10:30:00Z"
```


## Tests

The `src/test/java/com/sonatus/intern` directory contains the `InternApplicationTests.java` file, which includes two tests I have written. The first verifies that the POST request works correctly, and the second test tests the full functionality of the API - from submitting logs to retrieving them using filters.

Run tests with 

```bash
  mvn test
```
