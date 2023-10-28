package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;
import services.LoginService;
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

        return gson.toJson(result);
    }

}
