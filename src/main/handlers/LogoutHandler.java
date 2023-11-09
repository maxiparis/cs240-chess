package handlers;

import com.google.gson.Gson;
import responses.LogoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    private static LogoutHandler instance;

    private LogoutHandler(){

    }

    public static LogoutHandler getInstance(){
        if (instance == null){
            instance = new LogoutHandler();
        }
        return instance;
    }

    public Object handleRequest(Request request, Response response){
        Gson gson = new Gson();
        String authorizationToken = request.headers("Authorization");
//        System.out.println("authorizationToken = " + authorizationToken);

        LogoutService service = new LogoutService();
        LogoutResponse result = service.logout(authorizationToken);

        if (result.getMessage() != null) {
            if(result.getMessage().equals("Error: unauthorized") || result.getMessage().equals("Error: DB is empty") ||
                result.getMessage().equals("Error: the token was not found in DB.")){
                response.status(401);
            }
        }

        return gson.toJson(result);
    }

}

