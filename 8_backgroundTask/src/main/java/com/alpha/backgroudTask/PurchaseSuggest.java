package com.alpha.backgroudTask;

import com.alpha.component.EmailSender;
import com.alpha.component.Statistics;

import javax.mail.MessagingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class PurchaseSuggest extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        String time = config.getInitParameter("time");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /*从用户查询数据中分析查询率，如果某个作者查询超过阈值就发邮件给管理员*/
                Map<String, AtomicInteger> statistics = Statistics.getStatistics();
                String suggestion = statistics.entrySet().stream()
                        .filter(entry -> entry.getValue().get() > 5)
                        .map(Map.Entry::getKey)
                        .reduce((result, author) -> result + ";" + author)
                        .orElse("no suggestion");
                try {
                    EmailSender.sendEmail("suggestion for purchase", suggestion);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        long daySpan = 24 * 60 * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + time);
        String s = sdf.format(new Date());
        Date startTime = null;
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(task, startTime, daySpan);
        super.init(config);
    }
}
