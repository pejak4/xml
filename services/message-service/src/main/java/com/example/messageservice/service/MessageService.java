package com.example.messageservice.service;

import com.example.messageservice.model.Message;
import com.example.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public Message findBySenderId(Long id){return this.messageRepository.findOneBySenderId(id);}
    public Message findByReceiverId(Long id){return this.messageRepository.findOneByReceiverId(id);}

    @JmsListener(destination = "MessageQueue", containerFactory = "myFactory")
    public void receiveMessage(Message message){
        System.out.println(message);
        messageRepository.save(message);
    }

}
