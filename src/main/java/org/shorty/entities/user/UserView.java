package org.shorty.entities.user;

import org.shorty.utils.ObjectView;

public class UserView extends ObjectView {
    private Long id;
    private String name;
    private String email;
    private String createdAt;

    public UserView(User user) {
        super(user);

        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt().toString();
    }
}
