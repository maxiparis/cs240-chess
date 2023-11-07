package handlers;

import DAO.AuthDAO;
import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private static CreateGameHandler instance;

    private CreateGameHandler(){

    }

    public static CreateGameHandler getInstance(){
        if (instance == null){
            instance = new CreateGameHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){

        Gson gson = new Gson();
        String requestBody = request.body();

        String authorizationToken = request.headers("Authorization");

        CreateGameRequest createRequest = (CreateGameRequest) gson.fromJson(requestBody, CreateGameRequest.class);
        CreateGameService service = new CreateGameService();
        CreateGameResponse result = service.createGame(createRequest, authorizationToken);

        if(result.getMessage() != null){
            response.status(401);
        }


        return gson.toJson(result);
    }

}
