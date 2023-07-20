package org.shorty.entities.url;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "url_hash", nullable = false, unique = true)
    private String urlHash;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "user_id")
    private Long userId;

    // Default constructor
    public Url() {}

    public Url(String originUrl, Long user_id) {
        this.userId    = user_id;
        this.originUrl = originUrl;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getUrlHash() {
        return urlHash;
    }

    public Long getUserId() { return this.userId; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public void setShortUrl(String urlHash) {
        this.urlHash = urlHash;
    }
}
