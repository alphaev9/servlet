package com.alpha.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/wsChannel", configurator = GetHttpSessionConfigurator.class, encoders = Encoder.class)
public class WsMessageChannel {
    private Session session;

    public Session getSession() {
        return session;
    }

    @OnOpen
    public void wsOpen(Session session, EndpointConfig config) {
        System.out.println("ws opened: " + session.getId() + "  " + this);
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.session = session;
        httpSession.setAttribute("WsMessageChannel", this);
    }
    @OnClose
    public void wsClose(Session session) {
        System.out.println("ws closed: " + session.getId() + "  " + this);
    }
  /*  @OnError
    public void wsError(Throwable throwable){
        System.out.println("error........"+throwable.getMessage());
    }*/

    public void handle(Handler handler){
        handler.handle(session);
    }


}
