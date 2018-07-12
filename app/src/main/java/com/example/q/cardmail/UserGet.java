package com.example.q.cardmail;

public class UserGet {
    String id, name, email;

    public UserGet(String id, String name, String email) {
        this.id = id;

        this.name = name;
        this.email = email;
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
}
