package com.example.demo.service;

import com.example.demo.dto.CommentAddDTO;
import com.example.demo.dto.UserIdDTO;
import com.example.demo.model.Car;
import com.example.demo.model.Comment;
import com.example.demo.repository.AdvertisementRepository;
import com.example.demo.repository.CommentRepository;
import com.soapclient.api.domain.ClientRequestComment;
import com.soapclient.api.domain.ServerRespond;
//import javafx.beans.binding.StringExpression;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    @Autowired
    private AdvertisementRepository advertisementRepository;


    public List<Comment> findAllByCarId(Long id) {
        List<Comment> comments = this.commentRepository.findAllByCarId(id);
        for(Comment c : comments) {
            c.setDescriptionComment(StringEscapeUtils.escapeHtml4(c.getDescriptionComment()));
        }
        return comments;
    }

    public void save(ClientRequestComment commentAdd) {
        Car c = advertisementRepository.findOneById(commentAdd.getCarId());

        Comment com = Comment.builder().carId(c.getSecondId()).
                descriptionComment(commentAdd.getDescriptionComment()).fromUserId(commentAdd.getFromUserId()).build();

        template = new WebServiceTemplate(marshaller);
        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8081/ws", commentAdd);

        com.setSecondId(respond.getId());

        this.commentRepository.save(com);
    }

    public HashMap getCountComment(UserIdDTO id) {
        HashMap<Long, Integer> mapa = new HashMap<Long, Integer>();

        List<Comment> comments = this.commentRepository.findAll();
        for(Comment c : comments) {
            Car car = this.advertisementRepository.findOneById(c.getCarId());
            if(car.getUserId().equals(id.getUserId())){
                int j=0;
                for(int i=0; i<comments.size(); i++){
                    if(comments.get(i).getCarId() == car.getId()){
                        j++;
                    }
                }
                mapa.put(car.getId(), j);
            }
        }
        return mapa;
    }

//    public void updateData() {
//        List<Comment> commentList = this.commentRepository.findAll();
//        List<ClientRequestComment> comments;
//        ClientRequestComment comment = new ClientRequestComment();
//        for(Comment c : commentList) {
//            comment.setDescriptionComment(c.getDescriptionComment());
//            comment.setCarId(c.getCarId());
//            comment.setFromUserId(c.getFromUserId());
//
//            comments.add(comment);
//        }
//    }
}
