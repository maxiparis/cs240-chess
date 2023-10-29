package handlers;

import com.google.gson.Gson;
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
        System.out.println("request body: " + requestBody);

        LoginRequest loginRequest = (LoginRequest) gson.fromJson(requestBody, LoginRequest.class);
        LoginService service = new LoginService();
        LoginResponse result = service.login(loginRequest);

        if(result.getMessage() == "Error: the username is not in the DB." || result.getMessage() == "Error: unauthorized"){
            response.status(401);
        }
//        else if (result.getMessage() == "Error: bad request") {
//            response.status(400);
//        }


        return gson.toJson(result);
    }
}
