package net;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class WebSocketCommunicator extends Endpoint {
    public Session session;
    public WebSocketContainer container;
    public WebSocketCommunicator(){
        try {
            URI uri = new URI("ws://localhost:1233/connect");
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    System.out.println("\nWebSocketCommunicator Received: " + message);
                }
            });
        } catch (Exception e) {
            System.out.println("WebSocketCommunicator cannot connect: " + e.getMessage());
        }
    }

    public void send(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    @Override
    public void onClose(Session session, CloseReason closeReason) {

    }

    @Override
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }
}
