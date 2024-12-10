package com.sameerasw.ticketin.server.dto;

public class LoginResponse {
    private String token;
    private String type;
    private Long userId;
    private String name;
    private String email;
    private String userType;

    public LoginResponse(String token, Long userId, String name, String email, String userType) {
        this.token = token;
        this.type = "Bearer";
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public String getToken() { return token; }
    public String getType() { return type; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUserType() { return userType; }
}