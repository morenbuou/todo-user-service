package com.thoughtworks.user.user.service;

import com.thoughtworks.user.user.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.thoughtworks.user.user.service.LoginService.SECRET_KEY;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    String token;

    @Autowired
    LoginService loginService;

    @Before
    public void setUp() throws Exception {
        token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("userId", 1l)
                .compact();
    }

    @Test
    public void getUserByAuthentication() {
        User user;
        try {
            user = loginService.getUserByAuthentication(token);
        } catch (JwtException e) {
            throw e;
        }

        assertEquals(user.getId(), (Long)1l);
    }

    @Test(expected = JwtException.class)
    public void getUserByAuthenticationShouldFailed() {
        User user;
        try {
            user = loginService.getUserByAuthentication("testWrong");
        } catch (JwtException e) {
            throw e;
        }
        assertEquals(user.getId(), (Long)1l);
    }

    @Test
    public void generateAuthentication() {
        try {
            loginService.generateAuthentication(User.builder().id(1l).build());
        } catch (JwtException e) {
            throw e;
        }
    }
}