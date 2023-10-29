package handlers;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGameService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class JoinGameHandler {
    private static JoinGameHandler instance;

    private JoinGameHandler(){

    }

    public static JoinGameHandler getInstance(){
        if (instance == null){
            instance = new JoinGameHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){
        Gson gson = new Gson();
        String requestBody = request.body();
        String authorizationToken = request.headers("Authorization");



        JoinGameRequest registerRequest = (JoinGameRequest) gson.fromJson(requestBody, JoinGameRequest.class);
        JoinGameService service = new JoinGameService();
        JoinGameResponse result = service.joinGame(registerRequest, authorizationToken);

        if(result.getMessage() == "Error: already taken") {
            response.status(403);
        } else if (result.getMessage() == "Error: bad request") {
            response.status(400);
        } else if (result.getMessage() == "Error: unauthorized") {
            response.status(401);
        }


        return gson.toJson(result);
    }
}
