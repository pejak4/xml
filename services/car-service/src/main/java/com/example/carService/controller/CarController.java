package com.example.carService.controller;

import com.example.carService.dto.*;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.service.*;
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

    @Autowired
    private RentalService rentalService;

    @Autowired
    private OccupancyService occupancyService;

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
        return new ResponseEntity<>(this.advertisementService.save(car), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/getSingleCar")
    public ResponseEntity<?> getSingleCar(@RequestParam Long id) {
        return new ResponseEntity<>(this.carService.findOneCar(id), HttpStatus.OK);
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/acceptRentalRequest")
    public ResponseEntity<?> acceptRentalRequest(@RequestBody RentalRequestAcceptDeclineDTO r) {
        CarRentalRequest crr = this.rentalRequestService.findOneById(Long.parseLong(r.getRentalRequestId()));
        //this.rentalService.addRental(crr);
        this.rentalRequestService.acceptRentalRequest(Long.parseLong(r.getRentalRequestId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/declineRentalRequest")
    public ResponseEntity<?> declineRentalRequest(@RequestBody RentalRequestAcceptDeclineDTO r) {

        this.rentalRequestService.declineRentalRequest(Long.parseLong(r.getRentalRequestId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addOccupancy")
    public ResponseEntity<?> addOccupancy(@RequestBody OccupancyDTO o) {
        this.occupancyService.saveOccupancy(o);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/checkOccupancy")
    public ResponseEntity<?> checkOccupancy(@RequestBody OccupancyDTO o) {
        return new ResponseEntity<>(this.occupancyService.findOccupancyAndRequest(o.getStartDate(), o.getStartDate(), o.getCarId()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/declineRentalRequestWhenPaid")
    public ResponseEntity<?> declineRentalRequestWhenPaid(@RequestBody RentalRequestDTO rentalRequest) {
        this.rentalRequestService.declineRentalRequestWhenPaid(rentalRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000") //Kada se plati, napravicemo novi redu CarRentalDaty
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addRental")
    public ResponseEntity<?> addRental(@RequestBody RentalRequestAcceptDeclineDTO r) {
        CarRentalRequest crr = this.rentalRequestService.findOneById(Long.parseLong(r.getRentalRequestId()));
        this.rentalService.addRental(crr);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/ifHaveReservedRentalRequest")
    public ResponseEntity<?> ifHaveReservedRentalRequest(@RequestBody RentalRequestIfHaveReservedDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.ifHaveReservedRentalRequest(rentalRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsLoggedUser")
    public ResponseEntity<?> getAllRentalRequestsLoggedUser(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsLoggedUser(rentalRequest.getUserId()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUser")
    public ResponseEntity<?> getAllRentalRequestsForUser(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUser(rentalRequest.getUserId()), HttpStatus.OK);
    }

}
