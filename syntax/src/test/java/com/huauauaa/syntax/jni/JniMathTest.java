package com.huauauaa.syntax.jni;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

@EnabledIf("jniLibraryPresent")
class JniMathTest {

    @SuppressWarnings("unused")
    private static boolean jniLibraryPresent() {
        Path lib = Path.of("target", "jni", System.mapLibraryName("jnidemo"));
        return Files.isRegularFile(lib);
    }

    @Test
    void incrementDelegatesToNativeCode() {
        assertEquals(4, JniMath.increment(3));
    }

   
}
