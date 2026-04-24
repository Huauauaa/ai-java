# Development

Build, test, and run the **ai-java** Maven reactor locally.

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

## Tests

Run all tests in every module:

```bash
mvn test
```

Run tests for one module:

```bash
mvn -pl syntax test
mvn -pl server test
```

Run a **single test class** or **one method** (Surefire `test` property):

```bash
mvn -pl syntax test -Dtest=HelloWorldTest
mvn -pl syntax test -Dtest=JniMathTest#incrementDelegatesToNativeCode
```

### JNI / JNL tests (`syntax` module)

`JniMathTest` runs only when the native library is present. See **[JNL usage](jnl.md)** for how to build it and call `JniMath` from Java.

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
├── docs/                   # markdown guides (develop, jnl, …)
├── pom.xml                 # aggregator parent
├── syntax/                 # library module
└── server/                 # Spring Boot module
```

---

[← README](../README.md) · [JNL usage →](jnl.md)
