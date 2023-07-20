package org.shorty.entities.url;

import org.shorty.utils.ObjectView;

public class UrlView extends ObjectView {
    private String original_url;
    private String short_url;
    private String created_at;
    private Long user_id;

    public UrlView(Url url) {
        super(url);

        this.original_url = url.getOriginUrl();
        this.short_url = url.getUrlHash();
        this.created_at = url.getCreatedAt().toString();
        this.user_id = url.getUserId();
    }
}
