package server;

import spark.Spark;
public class Server {
    public static void main(String[] args) {
        System.out.println("Listening.... Please go to http://localhost:4567/hello\n");
        Spark.get("/hello", (req, res) -> "Hello BYU!");
    }

}
//        get("/", (request, response) -> {
//            // Show something
//        });
//
//        post("/", (request, response) -> {
//            // Create something
//        });
//
//        put("/", (request, response) -> {
//            // Update something
//        });
//
//        delete("/", (request, response) -> {
//            // Annihilate something
//        });
//
//        options("/", (request, response) -> {
//            // Appease something
//        });

