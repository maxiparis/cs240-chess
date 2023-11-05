package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.ClearApplicationResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import services.ClearApplicationService;
import services.LoginService;
import services.RegisterService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private static ClearHandler instance;

    private ClearHandler(){ }

    public static ClearHandler getInstance(){
        if(instance == null){
            instance = new ClearHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){
        Gson gson = new Gson();

        ClearApplicationService service = new ClearApplicationService();
        ClearApplicationResponse result = service.clearApplication();

        return gson.toJson(result);
    }
}
