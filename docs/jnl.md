# JNL usage

**JNL** in this repo means the **JNI** (Java Native Interface) demo in the **`syntax`** module: Java calls into a small native shared library (`libjnidemo`) that implements `value + 1`.

| Piece | Location |
| ----- | -------- |
| Java API | `com.huauauaa.syntax.jni.JniMath` |
| Native source | `syntax/src/main/native/jnidemo.c` |
| Build script | `syntax/scripts/build-jni.sh` |
| Built library | `syntax/target/jni/libjnidemo.dylib` (macOS) or `libjnidemo.so` (Linux) |

## Build the native library

From the repository root (uses Maven `jni` profile → runs the script after `compile`):

```bash
mvn -pl syntax -Pjni compile
```

Or run the script yourself from `syntax/` (requires `JAVA_HOME` and `clang`/`gcc`; macOS can auto-fill `JAVA_HOME` via `/usr/libexec/java_home`):

```bash
cd syntax && bash scripts/build-jni.sh
```

## Call it from Java

1. Ensure **`syntax/target/jni/libjnidemo.{dylib,so}`** exists (build step above).
2. Run the JVM with **working directory** `syntax/` when loading from tests or a small main, or copy the library to a known path and extend `JniMath` / your own `System.load` logic as needed.

```java
import com.huauauaa.syntax.jni.JniMath;

int next = JniMath.increment(3); // 4
```

`JniMath` loads the library by absolute path resolved from `target/jni/` relative to the process current working directory, so **paths depend on where you start the JVM** (Maven tests use the `syntax` module directory as the base dir).

## Verify with tests

```bash
mvn -pl syntax -Pjni clean test
```

After **`mvn clean`** without `-Pjni`, the library is removed and **`JniMathTest` is skipped** until you build with `-Pjni` again.

---

[← Development guide](develop.md) · [← README](../README.md)
