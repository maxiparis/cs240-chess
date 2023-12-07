package handlers;

import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import typeAdapters.ChessPieceDeserializer;
import typeAdapters.UserGameCommandDeserializer;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.userCommands.JoinObserverMessage;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import services.AuthTokenValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebSocket
public class WebSocketRequestHandler {
    Map<String, Connection> connectionsByAuthToken = new HashMap<>();
    Map<Integer, List<Connection>> connectionsByGameId = new HashMap<>();
    @OnWebSocketError
    public void onError(Session session, Throwable throwable) throws IOException {

    }


    @OnWebSocketMessage
    public void onMessage(Session session, String jsonMessage) throws Exception {
        System.out.println("Server received a message: " + jsonMessage);
        UserGameCommand command = readJson(jsonMessage);
        Connection connection = getConnection(command.getAuthString(), session);

        if(connection != null){
            switch (command.getCommandType()){
                case JOIN_PLAYER -> join(connection, command);
                case JOIN_OBSERVER -> {}
                case MAKE_MOVE -> {}
                case LEAVE -> {}
                case RESIGN -> {}
            }
        }

//        session.getRemote().sendString("WebSocketRequestHandler received a message: " + message);
    }

    private void join(Connection connection, UserGameCommand command) throws DataAccessException, IOException {
        JoinPlayerMessage joinCommand = (JoinPlayerMessage) command;

        //TODO: find out how to update the connectionsByGameId

        Game gameFromDB = GameDAO.getInstance().findGameById(joinCommand.getGameID());
        ChessGameImpl game = gameFromDB.getGame();

        //Server sends a LOAD_GAME message back to the root client.
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);

        String messageForRootClient = new Gson().toJson(loadGameMessage);
        send(connection.session, messageForRootClient);

        //Server sends a Notification message to all other clients in that game informing them what color
            // the root client is joining as.


    }

    private Connection getConnection(String authToken, Session session) throws DataAccessException {
        Connection connection = connectionsByAuthToken.get(authToken);
        if (connection == null){
            AuthTokenValidator validator = new AuthTokenValidator();
            validator.validateAuthToken(authToken);

            connection = new Connection(authToken, session);
            connectionsByAuthToken.put(authToken, connection);
        }
        return connection;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println("Connected");
    }

    public void send(Session session, String message) throws IOException {
        session.getRemote().sendString(message);
    }

    private UserGameCommand readJson(String jsonMessage) {
        UserGameCommand command = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(jsonMessage, UserGameCommand.class);
        return command;
    }

    private record Connection (String token, Session session) { }
}
