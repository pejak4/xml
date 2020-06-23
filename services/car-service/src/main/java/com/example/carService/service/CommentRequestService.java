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
import com.soapserveryt.api.soap.ClientRequestComment;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class CommentRequestService {
    @Autowired
    private CommentRequestRepository commentRequestRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public CommentCarRequest addCommentCarRequest(CommentCarRequestDTO commentCarRequestDTO) {
        CommentCarRequest ccr = CommentCarRequest.builder().carId(commentCarRequestDTO.getCarId()).fromUserId(commentCarRequestDTO.getFromUserId())
                .descriptionComment(commentCarRequestDTO.getDescriptionComment()).build();

        return this.commentRequestRepository.save(ccr);
    }

    public List<CommentCarRequest> findAll() {
        List<CommentCarRequest> comments = this.commentRequestRepository.findAll();
        for(CommentCarRequest c : comments) {
            c.setDescriptionComment(StringEscapeUtils.escapeHtml4(c.getDescriptionComment()));
        }
        return comments;
    }

    public void acceptCommentRequest(CommentRequestAcceptDeclineDTO commentRequestAcceptDeclineDTO) {
        CommentCarRequest ccr = this.commentRequestRepository.findOneById(commentRequestAcceptDeclineDTO.getCommentRequestId());

        Comment c = Comment.builder().carId(ccr.getCarId()).fromUserId(ccr.getFromUserId())
                .descriptionComment(ccr.getDescriptionComment()).build();

        this.commentRepository.save(c);
        this.commentRequestRepository.delete(ccr);

        ClientRequestComment com = new ClientRequestComment();
        com.setCarId(c.getCarId());
        com.setDescriptionComment(c.getDescriptionComment());
        com.setFromUserId(c.getFromUserId());

        template = new WebServiceTemplate(marshaller);
        template.marshalSendAndReceive("http://localhost:8080/ws", com);

        return;
    }

    public void declineCommentRequest(CommentRequestAcceptDeclineDTO commentRequestAcceptDeclineDTO) {
        CommentCarRequest ccr = this.commentRequestRepository.findOneById(commentRequestAcceptDeclineDTO.getCommentRequestId());

        this.commentRequestRepository.delete(ccr);

        return;
    }
}
