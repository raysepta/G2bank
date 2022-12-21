package com.example.g2bank.security.model;

public class JwtRespond {
    private String username;
    private Integer balance = 0;
    private String jwtToken;

    public JwtRespond() {

    }

    public JwtRespond(String jwtToken, String username, Integer balance) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.balance = balance;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
