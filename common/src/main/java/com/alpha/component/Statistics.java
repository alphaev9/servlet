package com.alpha.component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private static Map<String, AtomicInteger> statistics = new HashMap<>();

    public static void doStatistic(String key) {
        AtomicInteger count = statistics.get(key);
        if (count == null) {
            statistics.put(key, new AtomicInteger(0));
        } else {
            count.incrementAndGet();
        }
    }

    public static Map<String, AtomicInteger> getStatistics() {
        return statistics;
    }
}
