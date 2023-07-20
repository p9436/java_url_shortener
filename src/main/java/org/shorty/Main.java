package org.shorty;

import org.junit.Test;
import org.shorty.entities.url.Url;
import org.shorty.entities.url.UrlDao;
import org.shorty.entities.user.User;
import org.shorty.entities.user.UserDao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome!");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserDao userDao = new UserDao(entityManager);

        // Display all users before server starts
        List<User> users = userDao.getAllUsers();
        for (User u : users) {
            System.out.println(u.getId() + " " + u.getName());
        }

        Server.start();
    }

    @Test
    public void testCreateUser() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserDao userDao = new UserDao(entityManager);
        User userEntity = userDao.createUser("testUser", "test-user@example.com");
    }

    @Test
    public void testCreateUrl() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UrlDao urlDao = new UrlDao(entityManager);
        Url urlEntity = urlDao.createUrl("https://example.com", 1L);
    }
}
