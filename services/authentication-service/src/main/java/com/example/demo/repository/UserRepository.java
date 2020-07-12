package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findOneByEmailAndPassword(String email, String password);
    Users findOneByEmail(String email);
    Users findOneById(Long id);
    List<Users> findAllByEnabled(Boolean enabled);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from users u where u.id=:id")
    void deleteRequest(@Param("id") Long id);



}
