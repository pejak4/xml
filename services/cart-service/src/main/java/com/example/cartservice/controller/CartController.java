package com.example.cartservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CartController {

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/put")
    public ResponseEntity<?> put(){
        System.out.println("--------------------------------------");
        System.out.println("----------------UBACIO JE-------------");
        System.out.println("--------------------------------------");
        return  new ResponseEntity<>("MORES",HttpStatus.OK);
    }

}
