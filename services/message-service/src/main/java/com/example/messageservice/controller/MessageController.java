package com.example.messageservice.controller;


import com.example.messageservice.dto.MessageDTO;
import com.example.messageservice.model.Message;
import com.example.messageservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/porukeza")
    public ResponseEntity<?> vratiPoruke(@RequestParam String id){
        return new ResponseEntity<>(this.messageService.findByReceiverId(Long.parseLong(id)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/send")
    public void send(@RequestBody MessageDTO dto){
        System.out.println("Sending message.");
        Message message = new Message();
        message.setSenderId(dto.getSenderId());
        message.setReceiverId(dto.getReceiverId());
        message.setMessage(dto.getMessage());
        message.setMessageDate(new Timestamp(System.currentTimeMillis()));

        jmsTemplate.convertAndSend("MessageQueue", message);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/del")
    public ResponseEntity<?> deletee(@RequestParam("id")  String id){
        this.messageService.deleteMessage(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/porukeod")
    public ResponseEntity<?> porukee(@RequestParam("id") String id){
        return new ResponseEntity<>(this.messageService.findBySenderId(Long.parseLong(id)), HttpStatus.OK);
    }




}
