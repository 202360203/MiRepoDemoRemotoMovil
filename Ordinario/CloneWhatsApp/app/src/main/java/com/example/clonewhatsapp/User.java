package com.example.clonewhatsapp;

public class User {

    public String id;
    public String name;
    public String email;

    public String avatar_url;

    public User(String id, String name,
                String email, String avatar_url) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar_url = avatar_url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }
}
