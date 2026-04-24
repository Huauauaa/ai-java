# ai-java

Multi-module Maven project: shared **syntax** library and **server** Spring Boot application.

| Module   | Role                                      |
| -------- | ----------------------------------------- |
| `syntax` | Java library (e.g. language/syntax code)  |
| `server` | Spring Boot 3 web app, depends on `syntax` |

- **Group ID:** `com.huauauaa`
- **Java:** 17
- **Spring Boot:** 3.4.x (managed in the root POM)

## Prerequisites

- JDK 17+
- [Apache Maven](https://maven.apache.org/) 3.9+

## Build

From the repository root:

```bash
mvn clean package
```

Build only the server (and its dependency `syntax`):

```bash
mvn -pl server -am clean package
```

## Run the server

```bash
mvn -pl server spring-boot:run
```

Or run the executable JAR:

```bash
java -jar server/target/server-1.0.0-SNAPSHOT.jar
```

Default HTTP port is **8080** unless overridden in `server/src/main/resources/application.properties`.

## Project layout

```
ai-java/
├── pom.xml                 # aggregator parent
├── syntax/                 # library module
└── server/                 # Spring Boot module
```
