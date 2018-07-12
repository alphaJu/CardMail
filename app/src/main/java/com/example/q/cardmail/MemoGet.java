package com.example.q.cardmail;

public class MemoGet {
    String Title, Dates, Contents, Password;

    public void setPassword(String password) {
        Password = password;
    }

    public MemoGet(String title, String dates, String contents, String Password) {
        this.Title = title;
        this.Dates = dates;
        this.Contents = contents;
        this.Password = Password;

    }

    public String getTitle() {
        return Title;
    }

    public String getContents() {
        return Contents;
    }

    public String getDates() { return Dates; }

    public String getPassword() { return Password; }
}
