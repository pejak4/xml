package com.example.carService.controller;

import com.example.carService.dto.*;
import com.example.carService.service.AdvertisementService;
import com.example.carService.service.CarService;
import com.example.carService.service.RentalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private RentalRequestService rentalRequestService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/searchCars")
    public ResponseEntity<?> searchCars(@RequestBody CarSearchDTO csd) {
        return new ResponseEntity<>(this.carService.findCarSearch(csd.getStartDate(), csd.getEndDate(), csd.getStartCity()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/filterCars")
    public ResponseEntity<?> filterCars(@RequestBody CarFilterDTO cfd) {
        return new ResponseEntity<>(this.carService.findCarFilter(cfd.getBrand(), cfd.getModel(), cfd.getFuelType(),
                                    cfd.getTransmission(), cfd.getClassCar(), cfd.getPrice()[0], cfd.getPrice()[1],
                                    cfd.getMileage(), cfd.getPlannedMileage(), cfd.getCDW(),
                                    cfd.getCapacitySeatsForKids(), cfd.getCityLocation(), cfd.getStartDate(),
                                    cfd.getEndDate()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addAdvertisement")
    public ResponseEntity<?> addAdvertisement(@RequestBody AdvertisementDTO car) {
        return new ResponseEntity<>(this.advertisementService.save(car), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addRentalRequest")
    public ResponseEntity<?> addRentalRequest(@RequestBody RentalRequestDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.saveRentalRequest(rentalRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getRentalRequestByUserIdAndCar")
    public ResponseEntity<?> getRentalRequestById(@RequestBody RentalRequestIdAndCarDTO data) {
        return new ResponseEntity<>(this.rentalRequestService.findOneByUserIdAndCar(data), HttpStatus.OK);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsLogedUser")
    public ResponseEntity<?> getCarsLogedUser(@RequestBody UserIdDTO id) {
        return new ResponseEntity<>(this.carService.findAllByUserId(id.getUserId()), HttpStatus.OK);
    }

}
