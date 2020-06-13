package com.example.carService.service;

import com.example.carService.dto.CommentCarRequestDTO;
import com.example.carService.dto.CommentRequestAcceptDeclineDTO;
import com.example.carService.dto.RatingCarRequestDTO;
import com.example.carService.dto.RatingRequestAcceptDeclineDTO;
import com.example.carService.model.Car;
import com.example.carService.model.Comment;
import com.example.carService.model.CommentCarRequest;
import com.example.carService.model.RatingCarRequest;
import com.example.carService.repository.CarRepository;
import com.example.carService.repository.CommentRepository;
import com.example.carService.repository.CommentRequestRepository;
import com.example.carService.repository.RatingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentRequestService {
    @Autowired
    private CommentRequestRepository commentRequestRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CommentRepository commentRepository;

    public CommentCarRequest addCommentCarRequest(CommentCarRequestDTO commentCarRequestDTO) {
        CommentCarRequest ccr = CommentCarRequest.builder().carId(commentCarRequestDTO.getCarId()).fromUserId(commentCarRequestDTO.getFromUserId())
                .descriptionComment(commentCarRequestDTO.getDescriptionComment()).build();

        return this.commentRequestRepository.save(ccr);
    }

    public List<CommentCarRequest> findAll() {
        return this.commentRequestRepository.findAll();
    }

    public void acceptCommentRequest(CommentRequestAcceptDeclineDTO commentRequestAcceptDeclineDTO) {
        CommentCarRequest ccr = this.commentRequestRepository.findOneById(commentRequestAcceptDeclineDTO.getCommentRequestId());

        Comment c = Comment.builder().carId(ccr.getCarId()).fromUserId(ccr.getFromUserId())
                .descriptionComment(ccr.getDescriptionComment()).build();

        this.commentRepository.save(c);
        this.commentRequestRepository.delete(ccr);

        return;
    }

    public void declineCommentRequest(CommentRequestAcceptDeclineDTO commentRequestAcceptDeclineDTO) {
        CommentCarRequest ccr = this.commentRequestRepository.findOneById(commentRequestAcceptDeclineDTO.getCommentRequestId());

        this.commentRequestRepository.delete(ccr);

        return;
    }
}
