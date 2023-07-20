package org.shorty.controlles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.shorty.entities.url.Url;
import org.shorty.entities.url.UrlDao;
import org.shorty.entities.url.UrlView;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class UrlController {
    // POST /urls
    public static Route createUrlHandler = (Request request, Response response) -> {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        JsonObject jsonObject = JsonParser.parseString(request.body()).getAsJsonObject();

        System.out.println(jsonObject);

        // Extract the values from the JSON object
        String originUrl = jsonObject.get("url").getAsString();
        Long userId = jsonObject.get("user_id").getAsLong();
        UrlDao urlDao = new UrlDao(entityManager);

        Url urlEntity = urlDao.createUrl(originUrl, userId);
        System.out.println(userId + ": Created url hash: " + urlEntity.getUrlHash());

        response.status(201);
        response.type("application/json");

        return new UrlView(urlEntity).toJson();
    };

    public static Route getUrlHandler = (Request request, Response response) -> {
        System.out.println("request.params()");
        System.out.println(request.params());

        String urlHash = request.params("hash");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UrlDao urlDao = new UrlDao(entityManager);
        Optional<Url> optionalUrl = urlDao.findByHash(urlHash);
        if (optionalUrl.isPresent()) {
            response.type("application/json");
            Url urlEntity = optionalUrl.get();
            return new UrlView(urlEntity).toJson();
        } else {
            response.status(404);
            return "Url Not Found";
        }
    };
}
