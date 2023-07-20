package org.shorty;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import static org.shorty.controlles.UrlController.*;
import static org.shorty.controlles.UserController.*;

public class Server {
    public static void start() {
        Spark.port(3000);

        // Define your routes and handlers here
        Spark.get("/ping", (request, response) -> "pong");

        Spark.post("/users", createUserHandler);
        Spark.get("/users/:id", getUserHandler);

        Spark.post("/urls", createUrlHandler);
        Spark.get("/:hash", getUrlHandler);

        // Start the server
        Spark.awaitInitialization();
    }

    // GET /users/:id
    public static Route getUserHandler2 = (Request request, Response response) -> {
        System.out.println("hi from getUserHandler2");

        return "user.getName()";
    };
}
