package com.example.jsteam.model;

public class User {

    private Integer id;
    private String email;
    private String username;
    private String password;
    private String region;

    public User(Integer id, String email, String username,String password, String region) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.region = region;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
