package typeAdapters;

import chess.ChessGame;
import chess.ChessMoveImpl;
import chess.ChessPositionImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import webSocketMessages.userCommands.*;

import static org.junit.jupiter.api.Assertions.*;

class UserGameCommandDeserializerTest {
    UserGameCommandDeserializer deserializer = new UserGameCommandDeserializer();
    @Test
    void deserialize_JoinPlayerMessage() {
        JoinPlayerMessage message = new JoinPlayerMessage("11232-423-24", 2, ChessGame.TeamColor.WHITE);
        var json = new Gson().toJson(message);

        UserGameCommand command;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(json, UserGameCommand.class);
        System.out.println("");
        assertEquals(JoinPlayerMessage.class, command.getClass());
    }

    @Test
    void deserialize_JoinObserverMessage(){
        JoinObserverMessage message = new JoinObserverMessage("asdf-s-dfs-df-", 34);
        var json = new Gson().toJson(message);

        UserGameCommand command;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(json, UserGameCommand.class);
        System.out.println("");
        assertEquals(JoinObserverMessage.class, command.getClass());
    }

    @Test
    void deserialize_MakeMoveMessage(){
        MakeMoveMessage message = new MakeMoveMessage("asdf-asdf", 72,
                new ChessMoveImpl(new ChessPositionImpl(4,4), new ChessPositionImpl(3,3)));
        var json = new Gson().toJson(message);

        UserGameCommand command;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(json, UserGameCommand.class);
        System.out.println("");
        assertEquals(MakeMoveMessage.class, command.getClass());
    }

    @Test
    void deserialize_LeaveMessage(){
        LeaveMessage message = new LeaveMessage("test", 3);
        var json = new Gson().toJson(message);

        UserGameCommand command;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(json, UserGameCommand.class);
        assertEquals(LeaveMessage.class, command.getClass());
    }

    @Test
    void deserialize_ResignMessage(){
        ResignMessage message = new ResignMessage("test", 3);
        var json = new Gson().toJson(message);

        UserGameCommand command;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer())
                .create();
        command = gson.fromJson(json, UserGameCommand.class);
        assertEquals(ResignMessage.class, command.getClass());
    }


}