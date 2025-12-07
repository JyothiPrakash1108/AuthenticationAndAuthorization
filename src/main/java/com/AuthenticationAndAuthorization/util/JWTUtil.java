package com.AuthenticationAndAuthorization.util;

import com.AuthenticationAndAuthorization.entiy.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {
    public static String generateJwtToken(User fetchedUser) {
        return Jwts.builder()
                .setSubject(fetchedUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
                .claim("roles", "ROLE_" + fetchedUser.getRole())
                .signWith(SignatureAlgorithm.HS256, "mykey123mykey123mykey123mykey123mykey123mykey123mykey123mykey123mykey123mykey123")
                .compact();

    }
}
