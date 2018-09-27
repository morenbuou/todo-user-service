package com.thoughtworks.user.user.controller;

import com.thoughtworks.user.user.controller.exception.ForbiddenException;
import com.thoughtworks.user.user.controller.exception.NotFoundException;
import com.thoughtworks.user.user.model.User;
import com.thoughtworks.user.user.service.LoginService;
import com.thoughtworks.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    @GetMapping(value = "/authentication")
    public User getUserByToken() {
        System.out.println("test");
        return userService.getPrincipal();
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody User user) {
        User existUser = userService.getUserByName(user.getUsername());
        if (existUser != null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(HttpServletResponse response, @RequestBody User user) throws NotFoundException, ForbiddenException {
        String jwtToken = doLogin(user);
        response.addCookie(new Cookie(HttpHeaders.AUTHORIZATION, jwtToken));
        return ResponseEntity.ok().build();
    }

    private String doLogin(User user) throws NotFoundException, ForbiddenException {
        User existUser = userService.getUserByName(user.getUsername());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (existUser == null) {
            throw new NotFoundException();
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), existUser.getPassword())) {
            throw new ForbiddenException();
        }
        return loginService.generateAuthentication(existUser);
    }

}
