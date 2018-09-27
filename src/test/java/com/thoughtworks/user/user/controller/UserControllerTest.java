package com.thoughtworks.user.user.controller;

import com.thoughtworks.user.user.model.User;
import com.thoughtworks.user.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void getUserByToken() throws Exception {
        User user = User.builder().id(1l).username("user").password("pwd").build();

        given(userService.getPrincipal()).willReturn(user);

        mockMvc.perform(
                get("/users/authentication").cookie(new Cookie("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjF9.eYLgUZQjU6-JJrWxiEmNxq5ACtkMiBhEbf5KsJq8yWY")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1l))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.password").value("pwd"));
    }
}