package com.alpha.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener()
public class SearchRankingListener implements ServletRequestListener {

    public SearchRankingListener() {
    }


    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();

        String servletPath = request.getServletPath();

        if ("/query".equals(servletPath)) {
            String author = request.getParameter("author");
            Statistics.doStatistic(author);
            request.setAttribute("statistic", Statistics.getStatistics());
        }

    }

    private static class Statistics {
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
}
