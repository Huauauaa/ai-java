package com.huauauaa.syntax;

public final class HelloWorld {

    private HelloWorld() {}

    public static String greeting() {
        return "Hello, world";
    }

    public static void main(String[] args) {
        System.out.println(greeting());
    }
}
