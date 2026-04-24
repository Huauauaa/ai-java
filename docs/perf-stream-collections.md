# Stream collection performance (informal)

The **`syntax`** module includes `com.huauauaa.syntax.perf.StreamCollectionPerformanceTest`, which compares building a `List<Integer>` from `IntStream.range(0, n).boxed()` using:

1. **`Stream.toList()`** (Java 16+, immutable list)  
2. **`stream.collect(Collectors.toList())`** (mutable `ArrayList` by default)  
3. **Plain `for` loop** with `new ArrayList<>(n)` and `add(i)`

## Run the test

Timings are printed to Surefire output (stdout):

```bash
mvn -pl syntax test -Dtest=StreamCollectionPerformanceTest
```

## Conclusions (high level, not a substitute for JMH)

| Topic | Takeaway |
| ----- | -------- |
| Correctness | All three match the same reference list; the test asserts that. |
| Methodology | Warmup + averaged `System.nanoTime()` rounds in JUnit. GC, CPU governor, JIT tiering, and concurrent load can **change relative order** between runs and machines. |
| `toList()` vs `collect(toList())` | `toList()` uses a dedicated path and returns an **immutable** list; it often avoids collector overhead and can be **similar or faster** than `collect(Collectors.toList())` for simple sequential pipelines. |
| Streams vs `for` | A hand-written loop is easy to reason about, but here it still **boxes** every `int` into `Integer`. Streams do too in this setup—so **no universal “for is always fastest”** rule; measure on your JDK and hardware. |
| Production tuning | For library or hot-path decisions, use **[JMH](https://github.com/openjdk/jmh)** (or equivalent) on representative inputs and environments. |

---

[← Development guide](develop.md) · [← README](../README.md)
