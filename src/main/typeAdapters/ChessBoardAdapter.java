package typeAdapters;

import chess.ChessBoardImpl;
import chess.ChessGame;
import chess.ChessGameImpl;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ChessBoardAdapter extends TypeAdapter<ChessBoardImpl> {
    private Gson gson = new Gson();
    @Override
    public void write(JsonWriter jsonWriter, ChessBoardImpl chessBoard) throws IOException {
//        Gson gsonBuilder = new GsonBuilder()
//                .registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter())
//                .create();
//        ChessPiece boardTable[][]
        String serializedChessPiecesArray = gson.toJson(chessBoard.getBoardTable());
        jsonWriter.beginObject();
        jsonWriter.name("boardTable").value(serializedChessPiecesArray);
        jsonWriter.endObject();
    }

    @Override
    public ChessBoardImpl read(JsonReader jsonReader) throws IOException {
        ChessBoardImpl chessBoard = new ChessBoardImpl();
//        Gson gsonBuilder = new GsonBuilder()
//                .registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter())
//                .create();

        //TODO do 2d array to deserialize and insert into the correct position
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if(name.equals("boardTable")){
                jsonReader.beginArray();//outer array
                while (jsonReader.hasNext()){
                    jsonReader.beginArray(); //inner array
//                    for
                    String serializedPiece = jsonReader.nextString();
//                    if(serializedPiece.contains("R") || serializedPiece.contains(("r"))){
//
//                    }

                    jsonReader.endArray();
                }



                jsonReader.endArray();
            }


        }

        jsonReader.endObject(); // End reading the JSON object

        return chessBoard;
    }
}
