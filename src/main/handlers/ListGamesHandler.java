package handlers;
import com.google.gson.Gson;
import responses.ListGamesResponse;
import services.ListGamesService;
import spark.Request;
import spark.Response;
public class ListGamesHandler {
    private static ListGamesHandler instance;

    private ListGamesHandler(){

    }

    public static ListGamesHandler getInstance(){
        if (instance == null){
            instance = new ListGamesHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){
        Gson gson = new Gson();
        String requestBody = request.body();

        String authorizationToken = request.headers("Authorization");


        ListGamesService service = new ListGamesService();
        ListGamesResponse result = service.listGames(authorizationToken);

//        if(result.getMessage() == "Error: already taken"){
//            response.status(403);
//        } else if (result.getMessage() == "Error: bad request") {
//            response.status(400);
//        }

        return gson.toJson(result);
    }

}
