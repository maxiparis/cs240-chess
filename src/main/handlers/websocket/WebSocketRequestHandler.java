package handlers.websocket;

import DAO.AuthDAO;
import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import typeAdapters.UserGameCommandDeserializer;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.JoinObserverMessage;
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

        AuthToken rootAuthToken = AuthDAO.getInstance().findWithToken(command.getAuthToken());
        Connection connection = connections.addByAuthToken(rootAuthToken, session);

        if(connection != null){
            switch (command.getCommandType()){
                case JOIN_PLAYER -> joinPlayer(connection, command);
                case JOIN_OBSERVER -> joinObserver(connection, command);
                case MAKE_MOVE -> {}
                case LEAVE -> leave(connection.getAuthToken(), command);
                case RESIGN -> {}
            }
        }

        //FOR TESTING reception of messages:
        //session.getRemote().sendString("WebSocketRequestHandler received a message: " + message);
    }

    private void joinPlayer(Connection rootClientConnection, UserGameCommand command)
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
        String rootUsername = rootClientConnection.getAuthToken().getUsername();
        NotificationMessage notificationForOthers =
                new NotificationMessage("Player " + rootUsername + " has joined as " + rootColor + " player.");

        String notificationAsJson = new Gson().toJson(notificationForOthers);

        connections.broadcastToGame(joinCommand.getGameID(), rootClientConnection.getAuthToken(), notificationAsJson);
    }

    private void joinObserver(Connection rootClientConnection, UserGameCommand command)
            throws DataAccessException, IOException {
        JoinObserverMessage joinCommand = (JoinObserverMessage) command;

        connections.addByGameID(joinCommand.getGameID(), rootClientConnection);

        Game gameFromDB = GameDAO.getInstance().findGameById(joinCommand.getGameID());
        ChessGameImpl game = gameFromDB.getGame();

        //Server sends a LOAD_GAME message back to the root client.
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);

        String messageForRootClient = new Gson().toJson(loadGameMessage);
        rootClientConnection.send(messageForRootClient);

        //Server sends a Notification message to all other clients in that game informing them the rootClient
        //joined as observer
        String rootUsername = rootClientConnection.getAuthToken().getUsername();
        NotificationMessage notificationForOthers =
                new NotificationMessage("Player " + rootUsername + " has joined as observer.");

        String notificationAsJson = new Gson().toJson(notificationForOthers);

        connections.broadcastToGame(joinCommand.getGameID(), rootClientConnection.getAuthToken(), notificationAsJson);
    }

    private void leave(AuthToken authTokenLeaving, UserGameCommand command) throws DataAccessException, IOException {
        LeaveMessage leaveCommand = (LeaveMessage) command;
        connections.removeByAuthToken(authTokenLeaving, leaveCommand.getGameID());

        //remove from DB (will only work for players. with observers it will just update to the same game in the DB)
        Game gameFound = GameDAO.getInstance().findGameById((leaveCommand.getGameID()));
        String blackUsername = gameFound.getBlackUsername();
        String whiteUsername = gameFound.getWhiteUsername();



        if(blackUsername != null && blackUsername.equals(authTokenLeaving.getUsername())){
            blackUsername = null;
        } else if (whiteUsername != null && whiteUsername.equals(authTokenLeaving.getUsername())){
            whiteUsername = null;
        }

        GameDAO.getInstance().updateGame(gameFound.getGameName(), whiteUsername, blackUsername, gameFound.getGame());

        //send notifications to all others
        NotificationMessage message = new NotificationMessage("Player " + authTokenLeaving.getUsername() + " has left" +
                " the game.");
        String jsonMessage = new Gson().toJson(message);
        connections.broadcastToGame(leaveCommand.getGameID(), authTokenLeaving, jsonMessage);
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
    public void onClose(int statusCode, String reason) {
        // Method invoked when the WebSocket connection is closed
        System.out.println("WebSocket Closed. Status code: " + statusCode + ", Reason: " + reason);
        // Perform any cleanup or necessary actions here
    }
}
