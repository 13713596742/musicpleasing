package com.alsm.musicpleasing.model;

import java.util.List;
import java.util.Map;

public class User {
    private String userName;
    private String password;
    private String area;
    private  int year;
    private List<String> data;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public User() {
        super();
    }

    public User(String userName, String password, String area, int year) {
        this.userName = userName;
        this.password = password;
        this.area = area;
        this.year = year;
    }
}
