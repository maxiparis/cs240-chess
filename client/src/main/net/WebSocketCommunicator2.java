package net;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@ClientEndpoint
public class WebSocketCommunicator2 {
    public Session session;
    public WebSocketContainer container;
    public WebSocketCommunicator2() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, uri);
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connected.");
        try {
            session.getBasicRemote().sendText("Hello, Server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received from server: " + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }

}
