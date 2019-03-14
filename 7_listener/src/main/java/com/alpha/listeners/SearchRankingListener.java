package com.alpha.listeners;

import com.alpha.component.Statistics;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

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

        /*读取配置文件：获得所关注的url，和请求参数*/
        if ("/query".equals(servletPath)) {
            String author = request.getParameter("author");
            Statistics.doStatistic(author);
            request.setAttribute("statistic", Statistics.getStatistics());
        }

    }


}
