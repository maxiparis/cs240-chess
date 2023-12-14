package handlers;

import DAO.AuthDAO;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthToken;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    private static RegisterHandler instance;

    private RegisterHandler(){

    }

    public static RegisterHandler getInstance(){
        if (instance == null){
            instance = new RegisterHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){
        Gson gson = new Gson();
        String requestBody = request.body();

        RegisterRequest registerRequest = (RegisterRequest) gson.fromJson(requestBody, RegisterRequest.class);
        RegisterService service = new RegisterService();
        RegisterResponse result = service.register(registerRequest);

        if(result.getMessage() == "Error: already taken"){
            response.status(403);
        } else if (result.getMessage() == "Error: bad request") {
            response.status(400);
        }

        tryToInsertTokenJustCreated(result);


        return gson.toJson(result);
    }

    private static void tryToInsertTokenJustCreated(RegisterResponse result) {
        if(result.getMessage() == null){ //it was a valid call
            //add new authToken to the authsDB
            try {
                AuthDAO.getInstance().insert(new AuthToken(result.getUsername(), result.getAuthToken()));
            } catch (DataAccessException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
