package server;

import handlers.*;
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

        //Clear
        Spark.delete("/db", Server::handleClear);

        //Login
        Spark.post("/session", Server::handleLogin);

        //Logout
        Spark.delete("/session", Server::handleLogout);

        //Create Game
        Spark.post("/game", Server::handleCreateGame);

        //List Games
        Spark.get("/game", Server::handleListGames);

        //Join Game
        Spark.put("/game", Server::handleJoinGame);

    }

    private static Object handleJoinGame(Request request, Response response) {
        return JoinGameHandler.getInstance().handleRequest(request, response);
    }

    private static Object handleListGames(Request request, Response response) {
        return ListGamesHandler.getInstance().handleRequest(request, response);
    }

    private static Object handleCreateGame(Request request, Response response) {
        return CreateGameHandler.getInstance().handleRequest(request, response);
    }

    private static Object handleLogout(Request request, Response response) {
        return LogoutHandler.getInstance().handleRequest(request, response);
    }

    private static Object handleLogin(Request request, Response response) {
        return LoginHandler.getInstance().handleRequest(request, response);
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

