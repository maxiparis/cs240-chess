package handlers.websocket;

import DAO.AuthDAO;
import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import model.Game;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import typeAdapters.UserGameCommandDeserializer;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.LeaveMessage;
import webSocketMessages.userCommands.UserGameCommand;


import java.io.IOException;

@WebSocket
public class WebSocketRequestHandler {

    private ConnectionManager connections = new ConnectionManager();
    @OnWebSocketError
    public void onError(Session session, Throwable throwable) throws IOException {

    }


    @OnWebSocketMessage
    public void onMessage(Session session, String jsonMessage) throws Exception {
        System.out.println("Server received a message: " + jsonMessage);
        UserGameCommand command = readJson(jsonMessage);

        // TODO: maybe add here the connections to the connection Manager, since is the first point of contact.
        //  jsonMessage is going to include who is sending that message.

        Connection connection = connections.addByAuthToken(command.getAuthToken(), session);

        if(connection != null){
            switch (command.getCommandType()){
                case JOIN_PLAYER -> join(connection, command);
                case JOIN_OBSERVER -> leave(connection.getAuthToken(), command);
                case MAKE_MOVE -> {}
                case LEAVE -> {}
                case RESIGN -> {}
            }
        }

        //FOR TESTING reception of messages:
        //session.getRemote().sendString("WebSocketRequestHandler received a message: " + message);
    }

    private void leave(String authTokenLeaving, UserGameCommand command) {
        LeaveMessage leaveCommand = (LeaveMessage) command;
        connections.removeByAuthToken(authTokenLeaving, leaveCommand.getGameID());
    }

    private void join(Connection rootClientConnection, UserGameCommand command)
            throws DataAccessException, IOException {
        JoinPlayerMessage joinCommand = (JoinPlayerMessage) command;

        connections.addByGameID(joinCommand.getGameID(), rootClientConnection);
        
        Game gameFromDB = GameDAO.getInstance().findGameById(joinCommand.getGameID());
        ChessGameImpl game = gameFromDB.getGame();

        //Server sends a LOAD_GAME message back to the root client.
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);

        String messageForRootClient = new Gson().toJson(loadGameMessage);
        rootClientConnection.send(messageForRootClient);

        //Server sends a Notification message to all other clients in that game informing them what color
            // the root client is joining as.
        String rootColor = joinCommand.getPlayerColor().equals(ChessGame.TeamColor.WHITE) ? "White" : "Black";
        String rootUsername = AuthDAO.getInstance().findWithToken(joinCommand.getAuthToken()).getUsername();
        NotificationMessage notificationForOthers =
                new NotificationMessage("Player " + rootUsername + " has joined as " + rootColor + " player.");

        String notificationAsJson = new Gson().toJson(notificationForOthers);

        connections.broadcastToGame(joinCommand.getGameID(), rootClientConnection.getAuthToken(), notificationAsJson);
    }


    private UserGameCommand readJson(String jsonMessage) {
        UserGameCommand command = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(jsonMessage, UserGameCommand.class);
        return command;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println("Connected");
    }

    @OnWebSocketClose
    public void onClose(Session session) {

    }

}
