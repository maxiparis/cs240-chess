package typeAdapters;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.UserGameCommand;

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
}