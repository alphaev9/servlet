package com.alpha.websocket;

import javax.websocket.Session;

public interface Handler {
    void handle(Session session);
}
