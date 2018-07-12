package com.example.q.cardmail;

import java.util.HashMap;

public class UserPut {
    String id, name, email;

    public UserPut(HashMap<String, Object> parameters) {
        this.id = (String) parameters.get("id");
        this.name = (String) parameters.get("name");
        this.email = (String) parameters.get("email");
    }
}
