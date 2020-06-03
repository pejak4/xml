package com.example.advertisementservice.controller;

import com.example.advertisementservice.Service.AdvertisementService;
import com.example.advertisementservice.dto.AdvertisementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/addAdvertisement")
    public ResponseEntity<?> login(@RequestBody AdvertisementDTO car) {
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");

        return new ResponseEntity<>(this.advertisementService.save(car), HttpStatus.OK);
    }
}
