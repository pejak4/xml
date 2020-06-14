package com.example.carService.repository;

import com.example.carService.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCarId(Long car_id);
}
