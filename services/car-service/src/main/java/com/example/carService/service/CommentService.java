package com.example.carService.service;

import com.example.carService.model.Comment;
import com.example.carService.repository.CommentRepository;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;


    public List<Comment> findAllByCarId(Long id) {
        List<Comment> comments = this.commentRepository.findAllByCarId(id);
        for(Comment c : comments) {
            c.setDescriptionComment(StringEscapeUtils.escapeHtml4(c.getDescriptionComment()));
        }
        return comments;
    }
}
