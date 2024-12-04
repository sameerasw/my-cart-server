// src/main/java/com/sameerasw/ticketin/server/dto/LoginRequest.java
package com.sameerasw.ticketin.server.dto;

public class LoginRequest {
    private String email;
    private String password;
    private String userType;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}