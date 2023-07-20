package org.shorty.controlles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.shorty.entities.user.User;
import org.shorty.entities.user.UserDao;
import org.shorty.entities.user.UserView;
import spark.Request;
import spark.Response;
import spark.Route;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class UserController {
    public static Route createUserHandler = (Request request, Response response) -> {
        System.out.println("request.body():");
        System.out.println(request.body());

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Extract the values from the request.body
        JsonObject jsonObject = JsonParser.parseString(request.body()).getAsJsonObject();
        JsonObject userObject = jsonObject.getAsJsonObject("user");

        String userName = userObject.get("name").getAsString();
        String userEmail = userObject.get("email").getAsString();

        try {
            UserDao userDao = new UserDao(entityManager);
            User user = userDao.createUser(userName, userEmail);

            response.status(201);
            return new UserView(user).toJson();
        } catch (Exception e) {
            response.status(555);
            return e;
        }
    };

    // GET /users/:id
    public static Route getUserHandler = (Request request, Response response) -> {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserDao userDao = new UserDao(entityManager);
        Long userId = Long.parseLong(request.params("id"));
        Optional<User> optionalUser = userDao.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserView(user).toJson();
        } else {
            response.status(404); // Set the status code to 404 Not Found
            return "User Not Found";
        }
    };
}
