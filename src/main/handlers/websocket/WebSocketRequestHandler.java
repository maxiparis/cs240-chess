package handlers.websocket;

import DAO.AuthDAO;
import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import chess.ChessMoveImpl;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import services.AuthTokenValidator;
import typeAdapters.UserGameCommandDeserializer;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;


import javax.xml.validation.Validator;
import java.io.IOException;

@WebSocket
public class WebSocketRequestHandler {

    private ConnectionManager connections = new ConnectionManager();
    private Gson gson = new Gson();

    @OnWebSocketMessage
    public void onMessage(Session session, String jsonMessage) throws IOException {
        try {
            System.out.println("Server received a message: " + jsonMessage);
            UserGameCommand command = readJson(jsonMessage) ;

            //VALIDATE AUTHOKEN, GAMEID
            AuthTokenValidator authTokenValidator = new AuthTokenValidator();
            authTokenValidator.validateAuthToken(command.getAuthToken());

            AuthToken rootAuthToken = AuthDAO.getInstance().findWithToken(command.getAuthToken());
            Connection connection = connections.addByAuthToken(rootAuthToken, session);

            if(connection != null){
                switch (command.getCommandType()){
                    case JOIN_PLAYER -> joinPlayer(connection, command);
                    case JOIN_OBSERVER -> joinObserver(connection, command);
                    case MAKE_MOVE -> makeMove(connection, command);
                    case LEAVE -> leave(connection.getAuthToken(), command);
                    case RESIGN -> resign(connection, command);
                }
            }
        } catch (DataAccessException da) {
            //send an error message
            ErrorMessage errorMessage = new ErrorMessage(da.getMessage());
            session.getRemote().sendString(gson.toJson(errorMessage));
        } catch (Exception e) {
            System.out.println("onMessage had an error: " + e.getMessage());
        }
    }

    private void resign(Connection rootClientConnection, UserGameCommand command) throws DataAccessException, IOException {
        //Server marks the game as over (no more moves can be made). Game is updated in the database.
            //get the color of the root client
            //mark the game as won by the other color
        ResignMessage resignMessage = (ResignMessage) command;
        ChessGame.TeamColor colorWhoWon = null;

        Game gameFromDB = GameDAO.getInstance().findGameById(resignMessage.getGameID());
        if(gameFromDB.getGame().getTeamTurn().equals(ChessGame.TeamColor.WHITE_WON) ||
                gameFromDB.getGame().getTeamTurn().equals(ChessGame.TeamColor.BLACK_WON) ||
                gameFromDB.getGame().getTeamTurn().equals(ChessGame.TeamColor.STALEMATE)){
            //someone already won, you can't do that
            ErrorMessage errorMessage = new ErrorMessage("You cannot resign because the game is over.");
            rootClientConnection.send(gson.toJson(errorMessage));
            return;
        }

        if(rootClientConnection.getAuthToken().getUsername().equals(gameFromDB.getWhiteUsername())){
            gameFromDB.getGame().setTeamTurn(ChessGame.TeamColor.BLACK_WON);
            colorWhoWon = ChessGame.TeamColor.BLACK;
            GameDAO.getInstance().updateGame(gameFromDB.getGameName(), gameFromDB.getWhiteUsername(),
                    gameFromDB.getBlackUsername(), gameFromDB.getGame());
        } else if(rootClientConnection.getAuthToken().getUsername().equals(gameFromDB.getBlackUsername())){
            gameFromDB.getGame().setTeamTurn(ChessGame.TeamColor.WHITE_WON);
            colorWhoWon = ChessGame.TeamColor.WHITE;
            GameDAO.getInstance().updateGame(gameFromDB.getGameName(), gameFromDB.getWhiteUsername(),
                    gameFromDB.getBlackUsername(), gameFromDB.getGame());
        } else { // none of the names matches, send an error back
            ErrorMessage errorMessage = new ErrorMessage("You cannot resign, because you are not a player.");
            rootClientConnection.send(gson.toJson(errorMessage));
            return;
        }


        //Server sends a Notification message to all clients in that game informing them that the root client left.
        // This applies to both players and observers.
        NotificationMessage notificationMessage = new NotificationMessage
                ("Player " + rootClientConnection.getAuthToken().getUsername() + " resigned. " + colorWhoWon + " won.");
        connections.broadcastToGame(resignMessage.getGameID(), null,
                gson.toJson(notificationMessage));
    }

    private void makeMove(Connection rootClientConnection, UserGameCommand command) throws DataAccessException, IOException {
        MakeMoveMessage makeMoveMessage = (MakeMoveMessage) command;

        Game gameFromDB = GameDAO.getInstance().findGameById(makeMoveMessage.getGameID());

        //Verifying the game is not over
        if(gameFromDB.getGame().getTeamTurn().equals(ChessGame.TeamColor.WHITE_WON) ||
                gameFromDB.getGame().getTeamTurn().equals(ChessGame.TeamColor.BLACK_WON) ||
                gameFromDB.getGame().getTeamTurn().equals(ChessGame.TeamColor.STALEMATE)){
            //someone already won, you can't do a move
            ErrorMessage errorMessage = new ErrorMessage("You cannot make a move because the game is over.");
            rootClientConnection.send(gson.toJson(errorMessage));
            return;
        }


        //verify whose turn is it
        ChessGame.TeamColor rootClientTeam = null;
        String rootUsername = rootClientConnection.getAuthToken().getUsername();

        if(gameFromDB.getBlackUsername() != null && gameFromDB.getBlackUsername().equals(rootUsername )){
            rootClientTeam = ChessGame.TeamColor.BLACK;
        } else if (gameFromDB.getWhiteUsername() != null && gameFromDB.getWhiteUsername().equals(rootUsername )) {
            rootClientTeam = ChessGame.TeamColor.WHITE;
        }


        if(rootClientTeam != null && rootClientTeam.equals(gameFromDB.getGame().getTeamTurn())){
            //try to do the movement now that we know is the turn of the root
            try {
                gameFromDB.getGame().makeMove(makeMoveMessage.getMove());

                //if someone won after that move, update DB and send messages
                if(gameFromDB.getGame().isInCheckmate(ChessGame.TeamColor.WHITE)){
                    //black won
                    gameFromDB.getGame().setTeamTurn(ChessGame.TeamColor.BLACK_WON);
                } else if (gameFromDB.getGame().isInCheckmate(ChessGame.TeamColor.BLACK)) {
                    //white won
                    gameFromDB.getGame().setTeamTurn(ChessGame.TeamColor.WHITE_WON);
                } else if (gameFromDB.getGame().isInStalemate(ChessGame.TeamColor.WHITE) ||
                        gameFromDB.getGame().isInStalemate(ChessGame.TeamColor.BLACK)) {
                    //they are in stalemate
                    gameFromDB.getGame().setTeamTurn(ChessGame.TeamColor.STALEMATE);
                }

                //updating in DB
                GameDAO.getInstance().updateGame(gameFromDB.getGameName(), gameFromDB.getWhiteUsername(),
                        gameFromDB.getBlackUsername(), gameFromDB.getGame());

                Game updatedGameFromDB = GameDAO.getInstance().findGameById(makeMoveMessage.getGameID());


                //send load game to all players/observers in the game
                LoadGameMessage loadGameMessage = new LoadGameMessage(updatedGameFromDB.getGame());
                String jsonMessage = gson.toJson(loadGameMessage);

                connections.broadcastToGame(makeMoveMessage.getGameID(), null, jsonMessage);

                //send notification of the move to all others in the game ("player x has made the move: a2a5")
                NotificationMessage moveNotification = new NotificationMessage("Player " + rootUsername +
                        " made the move " + moveAsMessage(makeMoveMessage.getMove()));
                String notificationAsJson = gson.toJson(moveNotification);
                connections.broadcastToGame(makeMoveMessage.getGameID(), rootClientConnection.getAuthToken(),
                        notificationAsJson);
            } catch (InvalidMoveException e) {
                //the move was not valid, send message back saying is not his turn

                System.out.println("InvalidMoveException was triggered " + e.getMessage());
                ErrorMessage error = new ErrorMessage(e.getMessage());
                rootClientConnection.send(gson.toJson(error));
            } catch (IOException e) {
                System.out.println("Error. connections.broadcastToGame is failling.");
            }
        } else {
            //is not the turn of the root, send message back saying is not his turn
            ErrorMessage error = new ErrorMessage("It is not your turn yet");
            rootClientConnection.send(gson.toJson(error));
        }



    }

    private String moveAsMessage(ChessMoveImpl move) {
        //FORMAT -->  a4:a5:promotion     [col][row]:[col][row]:promotion

        StringBuilder builder = new StringBuilder();

        //convert the starPosition
            //col as letter
        int columnStart = move.getStartPosition().getColumn();
        String columnStartAsString = getColumnAsString(columnStart);
        builder.append(columnStartAsString);
            //row
        builder.append(move.getStartPosition().getRow() + ":");
        //convert the endPosition
            //col as letter
        int columnEnd = move.getEndPosition().getColumn();
        String columnEndAsString = getColumnAsString(columnEnd);
        builder.append(columnEndAsString);
            //row
        builder.append(move.getEndPosition().getRow());
        //promotion

        if(move.getPromotionPiece() != null){
            builder.append(":" + move.getPromotionPiece());
        }
        return builder.toString();
    }

    private static String getColumnAsString(int column) {
        String columnStartAsString = null;

        switch (column){
            case 1: {
                columnStartAsString = "a";
                break;
            }
            case 2: {
                columnStartAsString = "b";
                break;
            }
            case 3: {
                columnStartAsString = "c";
                break;
            }
            case 4: {
                columnStartAsString = "d";
                break;
            }
            case 5: {
                columnStartAsString = "e";
                break;
            }
            case 6: {
                columnStartAsString = "f";
                break;
            }
            case 7: {
                columnStartAsString = "g";
                break;
            }
            case 8: {
                columnStartAsString = "h";
                break;
            }
            default: {
                return null;
            }
        }
        return columnStartAsString;
    }

    private void joinPlayer(Connection rootClientConnection, UserGameCommand command)
            throws DataAccessException, IOException {
        JoinPlayerMessage joinCommand = (JoinPlayerMessage) command;

        //validate player is joining the correct side
        Game gameFromDB = GameDAO.getInstance().findGameById(joinCommand.getGameID());
        ChessGame.TeamColor colorChosen = joinCommand.getPlayerColor();

        //I ONLY want to allow the root to join if the team they chose is their own name
        boolean isRootUserNameInGameAlready = true;
        if(colorChosen.equals(ChessGame.TeamColor.WHITE)){
            isRootUserNameInGameAlready = gameFromDB.getWhiteUsername() != null &&
                    gameFromDB.getWhiteUsername().equals(rootClientConnection.getAuthToken().getUsername());
        } else if(colorChosen.equals(ChessGame.TeamColor.BLACK)){
            isRootUserNameInGameAlready = gameFromDB.getBlackUsername() != null &&
                    gameFromDB.getBlackUsername().equals(rootClientConnection.getAuthToken().getUsername());
        }

        if(!isRootUserNameInGameAlready){
            //send message back saying is
            ErrorMessage errorMessage = new ErrorMessage("That team is already taken.");
            rootClientConnection.send(gson.toJson(errorMessage));
            return;
        }

        ChessGameImpl game = gameFromDB.getGame();



        connections.addByGameID(joinCommand.getGameID(), rootClientConnection);


        //Server sends a LOAD_GAME message back to the root client.
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);

        String messageForRootClient = gson.toJson(loadGameMessage);
        rootClientConnection.send(messageForRootClient);

        //Server sends a Notification message to all other clients in that game informing them what color
        // the root client is joining as.
        String rootColor = joinCommand.getPlayerColor().equals(ChessGame.TeamColor.WHITE) ? "White" : "Black";
        String rootUsername = rootClientConnection.getAuthToken().getUsername();
        NotificationMessage notificationForOthers =
                new NotificationMessage("Player " + rootUsername + " has joined as " + rootColor + " player.");

        String notificationAsJson = gson.toJson(notificationForOthers);

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

        String messageForRootClient = gson.toJson(loadGameMessage);
        rootClientConnection.send(messageForRootClient);

        //Server sends a Notification message to all other clients in that game informing them the rootClient
        //joined as observer
        String rootUsername = rootClientConnection.getAuthToken().getUsername();
        NotificationMessage notificationForOthers =
                new NotificationMessage("Player " + rootUsername + " has joined as observer.");

        String notificationAsJson = gson.toJson(notificationForOthers);

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
        String jsonMessage = gson.toJson(message);
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
        System.out.println("Websocket opened");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        // Method invoked when the WebSocket connection is closed
        System.out.println("WebSocket Closed. Status code: " + statusCode + ", Reason: " + reason);
        // Perform any cleanup or necessary actions here
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) throws IOException {
        System.err.println("Error: " + throwable.getMessage());
    }
}
