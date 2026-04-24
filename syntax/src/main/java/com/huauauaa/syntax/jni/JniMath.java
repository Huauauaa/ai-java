package com.huauauaa.syntax.jni;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Minimal JNI demo: {@link #increment(int)} delegates to a small native shared library
 * ({@code libjnidemo.*}) built from {@code src/main/native/jnidemo.c}.
 *
 * <p>Build the library from the {@code syntax} module directory (or use the {@code jni} profile):
 *
 * <pre>{@code mvn -pl syntax -Pjni compile}</pre>
 */
public final class JniMath {

    private JniMath() {}

    private static volatile boolean loaded;

    private static native int increment0(int value);

    private static void ensureLoaded() {
        if (!loaded) {
            synchronized (JniMath.class) {
                if (!loaded) {
                    Path lib = Path.of("target", "jni", System.mapLibraryName("jnidemo"));
                    if (!Files.isRegularFile(lib)) {
                        throw new UnsatisfiedLinkError(
                                "Missing JNI library at "
                                        + lib.toAbsolutePath()
                                        + ". Build with: mvn -pl syntax -Pjni compile");
                    }
                    System.load(lib.toAbsolutePath().toString());
                    loaded = true;
                }
            }
        }
    }

    /** Returns {@code value + 1} using a JNI call into native code. */
    public static int increment(int value) {
        ensureLoaded();
        return increment0(value);
    }
}
