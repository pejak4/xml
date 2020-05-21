package com.project.userservice.repository;

import com.project.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmailAndPassword(String email, String password);
    User findOneByEmail(String email);
}
