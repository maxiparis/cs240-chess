package typeAdapters;
import chess.*;
import com.google.gson.*;
import model.Game;
import responses.ListGamesResponse;

import java.lang.reflect.Type;
import java.util.HashSet;


public class ListGamesResponseDeserializer implements JsonDeserializer {
    @Override
    public ListGamesResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Game.class, new GameDeserializer())
                .create();
        HashSet<Game> games = new HashSet<>();

        //"games": [ ... ]
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray gamesAsJson = jsonObject.getAsJsonArray("games");
        if(gamesAsJson == null){
            try {
                JsonObject messageJson = jsonObject.getAsJsonObject("message");
                String message = new Gson().fromJson(messageJson, String.class);
                return new ListGamesResponse(message, null);
            } catch (Exception e ) {
                return new ListGamesResponse(e.getMessage(), null);
            }

        }

        for (JsonElement gameJson : gamesAsJson) {
            // {
            //      "gameID": 1638,
            //      "blackUsername": "tom",
            //      "whiteUsername" : "max",
            //      "gameName": "testst",
            //      "game":  {
            //          "teamTurn": "WHITE",
            //          "board": { boardTable [...][...] }
            //      }
            Game deserializedGame = gson.fromJson(gameJson, Game.class);
            games.add(deserializedGame);
        }

        ListGamesResponse response = new ListGamesResponse(null, games);
        return response;
    }
}
