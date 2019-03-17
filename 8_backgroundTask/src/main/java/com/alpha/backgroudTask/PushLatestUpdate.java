package com.alpha.backgroudTask;

import com.alpha.websocket.WsMessageChannel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

public class PushLatestUpdate extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ServletContext context = getServletContext();
                Enumeration<String> attributes = context.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String element = attributes.nextElement();
                    Object attribute = context.getAttribute(element);
                    HttpSession session;
                    if (attribute instanceof HttpSession) {
                        session = (HttpSession) attribute;
                        WsMessageChannel wsChannel = (WsMessageChannel) session.getAttribute("WsMessageChannel");
                        wsChannel.handle(wsSession -> {
                            try {
                                wsSession.getBasicRemote().sendObject("test");
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (EncodeException e) {
                                e.printStackTrace();
                            }
                        });

                    }
                }
            }
        };
        timer.schedule(task, 0, 1 * 60 * 1000);
        super.init(config);
    }
}
