package com.thoughtworks.user.user.repository;

import com.thoughtworks.user.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);

}
