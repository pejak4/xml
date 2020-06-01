package com.example.searchCarservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchCarController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public ResponseEntity<?> login(){
        System.out.println("--------------------------------------");
        System.out.println("AAAAAAAAAAAAAA");
        System.out.println("--------------------------------------");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
