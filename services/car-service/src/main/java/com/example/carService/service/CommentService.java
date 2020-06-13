package com.example.carService.service;

import com.example.carService.model.Comment;
import com.example.carService.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;


    public List<Comment> findAllByCarId(Long id) {
        return this.commentRepository.findAllByCarId(id);
    }
}
