package handlers;

import DAO.AuthDAO;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthToken;
import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private static LoginHandler instance;

    private LoginHandler(){

    }

    public static LoginHandler getInstance(){
        if (instance == null){
            instance = new LoginHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){
        Gson gson = new Gson();
        String requestBody = request.body();

        LoginRequest loginRequest = (LoginRequest) gson.fromJson(requestBody, LoginRequest.class);
        LoginService service = new LoginService();
        LoginResponse result = service.login(loginRequest);

        if(result.getMessage() == "Error: the username is not in the DB." ||
                result.getMessage() == "Error: unauthorized" || result.getMessage() == "Error: The Users DB is empty."){
            response.status(401);
        }

        tryToInsertTokenJustCreated(result);

        return gson.toJson(result);
    }

    private static void tryToInsertTokenJustCreated(LoginResponse result) {
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
