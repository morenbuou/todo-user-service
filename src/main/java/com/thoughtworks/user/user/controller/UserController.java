package com.thoughtworks.user.user.controller;

import com.thoughtworks.user.user.model.User;
import com.thoughtworks.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users/authentication")
    public User getUserByToken() {
        System.out.println("test");
        return userService.getPrincipal();
    }

}
