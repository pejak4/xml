package com.example.messageservice.controller;


import com.example.messageservice.model.Message;
import com.example.messageservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/porukeza")
    public ResponseEntity<?> vratiPoruke(@RequestBody String str){
        return new ResponseEntity<>(this.messageService.findByReceiverId(Long.parseLong(str)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/send")
    public void send(@RequestBody Message message){
        System.out.println("Sending message.");

        jmsTemplate.convertAndSend("MessageQueue", message);
    }






}
