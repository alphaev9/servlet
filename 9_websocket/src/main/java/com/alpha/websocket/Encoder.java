package com.alpha.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

public class Encoder implements javax.websocket.Encoder.Text<Object> {
    @Override
    public String encode(Object o) throws EncodeException {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonElement message = gson.toJsonTree(o);
        jsonObject.add("message", message);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonObject.toString());
        return gson.toJson(jsonElement);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
