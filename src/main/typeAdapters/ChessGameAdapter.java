package typeAdapters;

import chess.ChessGameImpl;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class ChessGameAdapter extends TypeAdapter<ChessGameImpl> {
    @Override
    public void write(JsonWriter jsonWriter, ChessGameImpl chessGame) throws IOException {
        Gson gson = new Gson();
        String serializedBoard = gson.toJson(chessGame.getBoard());

        jsonWriter.beginObject();
        jsonWriter.name("teamTurn").value(chessGame.getTeamTurn().name());
        jsonWriter.name("board").value(serializedBoard);
        jsonWriter.endObject();
    }

    @Override
    public ChessGameImpl read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
