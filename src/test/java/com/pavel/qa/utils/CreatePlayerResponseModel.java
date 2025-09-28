package com.pavel.qa.utils;

public class CreatePlayerResponseModel {
    private Long id;
    private int age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}


