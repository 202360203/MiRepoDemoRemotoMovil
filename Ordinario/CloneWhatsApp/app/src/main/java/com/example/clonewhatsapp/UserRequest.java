package com.example.clonewhatsapp;

public class UserRequest {

    public String id;
    public String name;
    public String email;
    public String avatar_url;

    public UserRequest(
            String id,
            String name,
            String email,
            String avatarUrl) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar_url = avatarUrl;
    }
}
