package com.huauauaa.syntax.perf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Rough wall-clock comparison of filling a {@link List} from an {@link IntStream#range(int, int)}.
 * Not a substitute for JMH; JVM warmup and GC can skew results. Output is visible in Surefire logs.
 */
class StreamCollectionPerformanceTest {

    private static final int SIZE = 200_000;
    private static final int WARMUP = 15;
    private static final int ROUNDS = 40;

    @Test
    void streamToListVsCollectVsForLoop() {
        List<Integer> expected = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            expected.add(i);
        }

        for (int i = 0; i < WARMUP; i++) {
            assertEquals(expected, streamToList());
            assertEquals(expected, streamCollect());
            assertEquals(expected, plainForLoop());
        }

        long tStreamToList = averageNanos(this::streamToList);
        long tCollect = averageNanos(this::streamCollect);
        long tFor = averageNanos(this::plainForLoop);

        System.out.printf(
                "%nStreamCollectionPerformanceTest (n=%d, rounds=%d)%n"
                        + "  Stream.toList():                    %8.2f ms%n"
                        + "  stream.collect(Collectors.toList()): %8.2f ms%n"
                        + "  plain for + ArrayList:              %8.2f ms%n",
                SIZE,
                ROUNDS,
                tStreamToList / 1_000_000.0,
                tCollect / 1_000_000.0,
                tFor / 1_000_000.0);

        assertEquals(expected, streamToList());
        assertEquals(expected, streamCollect());
        assertEquals(expected, plainForLoop());
    }

    private long averageNanos(Runnable task) {
        long sum = 0;
        for (int i = 0; i < ROUNDS; i++) {
            long t0 = System.nanoTime();
            task.run();
            sum += System.nanoTime() - t0;
        }
        return sum / ROUNDS;
    }

    private List<Integer> streamToList() {
        return IntStream.range(0, SIZE).boxed().toList();
    }

    private List<Integer> streamCollect() {
        return IntStream.range(0, SIZE).boxed().collect(Collectors.toList());
    }

    private List<Integer> plainForLoop() {
        ArrayList<Integer> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
        return list;
    }
}
