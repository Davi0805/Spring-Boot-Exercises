package com.example.spring02.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String sessionId;
    private boolean logged;

    public User(String username, String sessionId)
    {
        this.username = username;
        this.sessionId = sessionId;
        this.logged = false;
    }

    public void setUsername(String username) {
        this.username = username.substring(7);
    }
}
