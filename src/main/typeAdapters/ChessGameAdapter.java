package typeAdapters;

import chess.ChessBoardImpl;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class ChessGameAdapter extends TypeAdapter<ChessGameImpl> {
    private Gson gson = new Gson();

    @Override
    public void write(JsonWriter jsonWriter, ChessGameImpl chessGame) throws IOException {
        //declaring how to serialize a board
        Gson gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ChessBoardImpl.class, new ChessBoardAdapter())
                .create();
        String serializedBoard = gson.toJson(chessGame.getBoard());

        jsonWriter.beginObject();
        jsonWriter.name("teamTurn").value(chessGame.getTeamTurn().name());
        jsonWriter.name("board").value(serializedBoard);
        jsonWriter.endObject();
    }

    @Override
    public ChessGameImpl read(JsonReader jsonReader) throws IOException {
        ChessGameImpl chessGame = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        jsonReader.beginObject();

        //declaring how to serialize & deserialize a board
        Gson gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ChessBoardImpl.class, new ChessBoardAdapter())
                .create();
        String serializedBoard = gson.toJson(chessGame.getBoard());

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if ("teamTurn".equals(name)) {
                chessGame.setTeamTurn(ChessGame.TeamColor.valueOf(jsonReader.nextString())); // Populate field1 from JSON
            } else if ("board".equals(name)) {
                //deserialize the json
                String boardAsJSON = jsonReader.nextString();
                ChessBoardImpl chessBoard = gson.fromJson(boardAsJSON, ChessBoardImpl.class);
                chessGame.setBoard(chessBoard);
            }
        }

        jsonReader.endObject(); // End reading the JSON object
        return chessGame; // Return the deserialized object
    }
}
