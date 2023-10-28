package server;

import handlers.ClearHandler;
import handlers.RegisterHandler;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.sound.sampled.Port;

public class Server {
    public static void main(String[] args) {

        Spark.externalStaticFileLocation("web");
        Spark.port(8080);
        System.out.println("Listening.... Please go to http://localhost:8080/\n");
        runHomePage();
        createRoutes();

    }

    private static void createRoutes() {
        //Register
        Spark.post("/user", Server::handleRegister);

        //clear
        Spark.delete("/db", Server::handleClear);


    }


    private static Object handleClear(Request request, Response response) {
        return ClearHandler.getInstance().handleRequest(request, response);
    }

    private static Object handleRegister(Request request, Response response) {
        return RegisterHandler.getInstance().handleRequest(request, response);
    }
    private static void runHomePage() {
        Spark.get("/", (req, res) -> ""
        );
    }

}

