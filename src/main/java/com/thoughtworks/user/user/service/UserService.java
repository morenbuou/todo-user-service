package com.thoughtworks.user.user.service;

import com.thoughtworks.user.user.model.User;
import com.thoughtworks.user.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Boolean createUser(User user) {
        if (userRepository.getByUsername(user.getUsername()) != null) {
            return false;
        }
        encryptPassword(user);
        userRepository.save(user);
        return true;
    }

    public User getUserByName(String username) {
        return userRepository.getByUsername(username);
    }

    private void encryptPassword(User user){
        String password = user.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        user.setPassword(password);
    }
}
