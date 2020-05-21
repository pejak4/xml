package com.project.searchservice.controller;

import com.project.searchservice.dto.CarSearchDTO;
import com.project.searchservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/searchCars")
    public ResponseEntity<?> searchCars(@RequestBody CarSearchDTO csd){
        return new ResponseEntity<>(this.carService.findCarSearch(csd.getStartDate(), csd.getEndDate(), csd.getStartCity()), HttpStatus.OK);
    }



}
