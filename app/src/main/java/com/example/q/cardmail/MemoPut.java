package com.example.q.cardmail;


import java.util.HashMap;

public class MemoPut {
    String Title, Dates, Contents, Password;

    public MemoPut(HashMap<String, Object> parameters) {
        this.Title = (String) parameters.get("Title");
        this.Dates = (String) parameters.get("Dates");
        this.Contents = (String) parameters.get("Contents");
        this.Password = (String) parameters.get("Password");
    }

}
