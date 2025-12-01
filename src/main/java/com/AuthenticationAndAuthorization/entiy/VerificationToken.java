package com.AuthenticationAndAuthorization.entiy;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;// UUID
    @OneToOne
    private User user;
    private Date expiry;
    public VerificationToken(){}

    public VerificationToken(Long id, String token, User user,Date expiry) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiry = expiry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }
}
