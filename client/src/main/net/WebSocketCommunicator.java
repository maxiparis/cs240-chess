package net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import typeAdapters.ServerMessageDeserializer;
import typeAdapters.UserGameCommandDeserializer;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

public class WebSocketCommunicator extends Endpoint {
    private ServerMessageObserver observer;
    public Session session;
    public WebSocketContainer container;
    public WebSocketCommunicator(ServerMessageObserver observer){
        try {
            this.observer = observer;
            URI uri = new URI("ws://localhost:1233/connect");
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String jsonMessage) {
                    ServerMessage serverMessage = readJson(jsonMessage);
                    observer.notify(serverMessage);
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

    private ServerMessage readJson(String jsonMessage) {
        ServerMessage message = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer())
                .create();
        message = gson.fromJson(jsonMessage, ServerMessage.class);
        return message;
    }
}
