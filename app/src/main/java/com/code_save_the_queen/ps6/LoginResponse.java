package com.code_save_the_queen.ps6;

public class LoginResponse {
    private String status;
    private long id;
    private String token;

    public LoginResponse(String status, long id, String token) {
        this.status = status;
        this.id = id;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}