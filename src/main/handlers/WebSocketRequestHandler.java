package handlers;

import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import typeAdapters.ChessPieceDeserializer;
import typeAdapters.UserGameCommandDeserializer;
import webSocketMessages.userCommands.JoinObserverMessage;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketRequestHandler {
    @OnWebSocketError
    public void onError(Session session, Throwable throwable) throws IOException {

    }


    @OnWebSocketMessage
    public void onMessage(Session session, String jsonMessage) throws Exception {
        System.out.println("Server received a message: " + jsonMessage);
        UserGameCommand command = readJson(jsonMessage);

//        session.getRemote().sendString("WebSocketRequestHandler received a message: " + message);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println("Connected");
    }

    public void send(Session session, String message) throws IOException {
        session.getRemote().sendString("WebSocketRequestHandler received a message: " + message);
    }

    private UserGameCommand readJson(String jsonMessage) {
        UserGameCommand command = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(jsonMessage, UserGameCommand.class);
        return command;
    }
}
