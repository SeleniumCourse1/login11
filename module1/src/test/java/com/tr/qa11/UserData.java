package com.tr.qa11;

public class UserData {
    private String email;
    private String pwd;

    @Override
    public String toString() {
        return "UserData{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public UserData setEmail(String email) {
        this.email = email;
        return  this;
    }

    public UserData setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }
}
