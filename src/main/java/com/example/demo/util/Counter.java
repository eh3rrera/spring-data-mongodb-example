package com.example.demo.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static AtomicInteger counter = new AtomicInteger();

    public static int getValue() {
        return counter.incrementAndGet();
    }
}
