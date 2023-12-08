package typeAdapters;

import chess.ChessBoardImpl;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import static org.junit.jupiter.api.Assertions.*;

class ServerMessageDeserializerTest {
    ServerMessageDeserializer deserializer = new ServerMessageDeserializer();

    @Test
    void deserialize_loadGame() {

        ChessGameImpl chessGame = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board = new ChessBoardImpl();
        board.resetBoard();
        chessGame.setBoard(board);
        LoadGameMessage message = new LoadGameMessage(chessGame);

        var json = new Gson().toJson(message);

        ServerMessage serverMessage;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer())
                .create();

        serverMessage = gson.fromJson(json, ServerMessage.class);
        assertEquals(LoadGameMessage.class, serverMessage.getClass());
    }

    @Test
    void deserialize_error() {

        ErrorMessage message = new ErrorMessage("test");

        var json = new Gson().toJson(message);

        ServerMessage serverMessage;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer())
                .create();

        serverMessage = gson.fromJson(json, ServerMessage.class);
        assertEquals(ErrorMessage.class, serverMessage.getClass());
    }

    @Test
    void deserialize_notifification() {

        NotificationMessage message = new NotificationMessage("notification test");

        var json = new Gson().toJson(message);

        ServerMessage serverMessage;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer())
                .create();

        serverMessage = gson.fromJson(json, ServerMessage.class);

        NotificationMessage converted = (NotificationMessage) serverMessage;
        assertEquals(NotificationMessage.class, serverMessage.getClass());
        assertEquals(message.getMessage(), converted.getMessage());
    }


}