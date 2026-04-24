# ai-java

Multi-module Maven project: shared **syntax** library and **server** Spring Boot application.

| Module   | Role                                      |
| -------- | ----------------------------------------- |
| `syntax` | Java library (e.g. language/syntax code)  |
| `server` | Spring Boot 3 web app, depends on `syntax` |

- **Group ID:** `com.huauauaa`
- **Java:** 17
- **Spring Boot:** 3.4.x (managed in the root POM)

## Documentation

| Doc | Description |
| --- | ----------- |
| [docs/develop.md](docs/develop.md) | Prerequisites, build, tests, run server, layout |
| [docs/jnl.md](docs/jnl.md) | JNL / JNI native library demo in `syntax` |
| [docs/perf-stream-collections.md](docs/perf-stream-collections.md) | `Stream.toList()` vs `collect(toList())` vs plain `for` (informal notes + test) |
| [docs/github-pages-javadoc.md](docs/github-pages-javadoc.md) | Publish aggregate Javadoc via GitHub Actions → GitHub Pages |

Quick start: `mvn clean package` and `mvn -pl server spring-boot:run` (see [develop.md](docs/develop.md) for detail).
