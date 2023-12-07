package handlers;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
@WebSocket
@ServerEndpoint(value = "/connect")
public class WebSocketRequestHandler2{
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("WebSocket opened: " + session.getId());
//    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);
        try {
            session.getBasicRemote().sendText("Server received: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }
}
