package com.huauauaa.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HelloWorldTest {

    @Test
    void greeting() {
        assertEquals("Hello, world", HelloWorld.greeting());
    }
}
