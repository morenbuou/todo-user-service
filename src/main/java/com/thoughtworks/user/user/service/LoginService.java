package com.thoughtworks.user.user.service;

import com.thoughtworks.user.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public static final byte[] SECRET_KEY = "wjh".getBytes();

    public User getUserByAuthentication(String signature) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(signature)
                .getBody();

        Long id = claims.get("userId", Long.class);
        return User.builder().id(id).username("wjh").build();
    }

    public String generateAuthentication(User user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("userId", user.getId())
                .compact();
    }
}
