package typeAdapters;

import chess.ChessBoardImpl;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameDeserializerTest {
    ChessGameDeserializer deserializer;



    @Test
    void deserialize() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessGame.class, new ChessGameDeserializer())
                .create();


        //serialize a chessGame
        ChessGameImpl game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        ChessBoardImpl board = new ChessBoardImpl();
        board.resetBoard();
        game.setBoard(board);

        //get the json
        String jsonString = gson.toJson(game);

        //try to deserialize
        ChessGameImpl chessGameDeserialized = gson.fromJson(jsonString, ChessGameImpl.class);
    }
}