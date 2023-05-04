package ru.praktikum.data;

public class TokenForAuth {
    private String token;

    public TokenForAuth() {
    }
    public TokenForAuth(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
