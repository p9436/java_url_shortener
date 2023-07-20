package org.shorty.entities.user;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private final EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates a new user with the given name and email in the database.
     *
     * @param name  The name of the user.
     * @param email The email of the user.
     * @return The created User object.
     */
    public User createUser(String name, String email) {
        User user = new User(name, email);

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle or re-throw the exception
            throw e;
        }
    }

    /**
     * Retrieves a user from the database based on the provided user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return An Optional containing the User object if found, or an empty Optional if not found.
     */
    public Optional<User> getUserById(Long userId) {
        User user = entityManager.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param userId       The ID of the user to update.
     * @param updatedUser The updated User object.
     * @return The updated User object.
     */
    public User updateUser(Long userId, User updatedUser) {
        User userEntity = entityManager.find(User.class, userId);
        if (userEntity != null) {
            userEntity.setName(updatedUser.getName());
            userEntity.setEmail(updatedUser.getEmail());
        }
        return userEntity;
    }

    /**
     * Deletes a user from the database based on the provided user ID.
     *
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                // Handle or re-throw the exception
                throw new RuntimeException("Failed to delete user.", e);
            }
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all User objects in the database.
     */
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
