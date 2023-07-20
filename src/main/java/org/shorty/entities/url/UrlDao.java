package org.shorty.entities.url;

import org.jetbrains.annotations.NotNull;
import org.shorty.utils.RandomString;

import javax.persistence.*;
import java.util.Optional;

public class UrlDao {
    private final EntityManager entityManager;
    private static final int HASH_LENGTH = 6;
    private static final String HASH_ALPHABET = "abcdefghjkmnpqrstuvwxyz";

    public UrlDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a new URL entity and store it in the database.
     * @param originUrl The original URL.
     * @param userId The user ID associated with the URL.
     * @return The newly created Url entity.
     */
    public Url createUrl(String originUrl, Long userId) {
        Url urlEntity = new Url(originUrl, userId);

        // Generate a random short URL hash
        RandomString randomString = new RandomString(HASH_ALPHABET) ;
        urlEntity.setShortUrl(randomString.generate(HASH_LENGTH));

        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();

            // Try inserting the URL with conflict handling until it succeeds
            while (insertUrlWithConflictHandling(urlEntity) == null) {
                urlEntity.setShortUrl(randomString.generate(HASH_LENGTH));
            }

            transaction.commit();
        } catch (Exception e) {
            // If any exception occurs during the transaction, rollback
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }

        return urlEntity;
    }

    /**
     * Find a URL entity by its URL hash.
     * @param hash The URL hash to search for.
     * @return The URL entity with the given hash, or null if not found.
     */
    public Optional<Url> findByHash(String hash) {
        TypedQuery<Url> query = entityManager.createQuery(
                "SELECT u FROM Url u WHERE u.urlHash = :hash", Url.class);
        query.setParameter("hash", hash);
        query.setMaxResults(1);
        try {
            Url url = query.getSingleResult();
            return Optional.of(url);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Insert the URL entity with conflict handling.
     * @param urlEntity The URL entity to be inserted.
     * @return The ID of the inserted URL entity if successful, otherwise null.
     */
    private Long insertUrlWithConflictHandling(@NotNull Url urlEntity) {
        String urlHash = urlEntity.getUrlHash();
        System.out.println("Inserting hash " + urlHash);

        String query = "INSERT INTO urls (origin_url, url_hash, user_id, created_at) " +
                       "VALUES (:url, :urlHash, :userId, NOW()) " +
                       "ON CONFLICT (url_hash) DO NOTHING RETURNING id";
        try {
            Object id = entityManager.createNativeQuery(query)
                    .setParameter("url", urlEntity.getOriginUrl())
                    .setParameter("userId", urlEntity.getUserId())
                    .setParameter("urlHash", urlHash)
                    .getSingleResult();
            return ((Integer) id).longValue();
        } catch(javax.persistence.NoResultException e) {
            System.out.println("Warning! " + urlHash + " is already in use");
            return null;
        }
    }
}
