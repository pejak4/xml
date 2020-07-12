package com.example.carService.controller;

import com.example.carService.dto.*;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.model.RentalRequestRole;
import com.example.carService.security.Acl;
import com.example.carService.service.*;
import com.soapserveryt.api.soap.ClientRequestRentalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private RatingRequestService ratingRequestService;

    @Autowired
    private CommentRequestService commentRequestService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private Acl acl;

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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/updateCarImage")
    public ResponseEntity<?> updateCarImage(@RequestBody ImageUpdateDTO imageUpdateDTO) {
        return new ResponseEntity<>(this.advertisementService.updateImage(imageUpdateDTO.getImage(), imageUpdateDTO.getCarId()), HttpStatus.OK);
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sentSoapRentalRequest")
    public ResponseEntity<?> sentSoapRentalRequest(@RequestBody ClientRequestRentalRequest rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.sentSoapRentalRequest(rentalRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getRentalRequestByUserIdAndCar")
    public ResponseEntity<?> getRentalRequestById(@RequestBody RentalRequestIdAndCarDTO data) {
        return new ResponseEntity<>(this.rentalRequestService.findAllByUserIdAndCar(data), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsLogedUser")
    public ResponseEntity<?> getCarsLogedUser(@RequestBody UserIdDTO id) {
        return new ResponseEntity<>(this.carService.findAllByUserId(id.getUserId()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/acceptRentalRequest")
    public ResponseEntity<?> acceptRentalRequest(@RequestBody RentalRequestAcceptDeclineDTO r) {
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

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, taj placeni oglas prelazi u stanje PAID
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/paidRentalRequest")
    public ResponseEntity<?> paidRentalRequest(@RequestBody RentalRequestAcceptDeclineDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.setRolePaidRentalRequest(Long.parseLong(rentalRequest.getRentalRequestId())) ,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/declineRentalRequestWhenPaid")
    public ResponseEntity<?> declineRentalRequestWhenPaid(@RequestBody RentalRequestDTO rentalRequest) {
        this.rentalRequestService.declineRentalRequestWhenPaid(rentalRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:3000") //Kada se plati, napravicemo novi oglas u tabeli CarRentalDate
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addRental")
//    public ResponseEntity<?> addRental(@RequestBody RentalRequestAcceptDeclineDTO r) {
//        CarRentalRequest crr = this.rentalRequestService.findOneById(Long.parseLong(r.getRentalRequestId()));
//        this.rentalService.addRental(crr);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @CrossOrigin(origins = "http://localhost:3000")//ako je taj auto vec rezervisan u tom periodu
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/ifHaveReservedRentalRequest")
    public ResponseEntity<?> ifHaveReservedRentalRequest(@RequestBody RentalRequestIfHaveReservedDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.ifHaveReservedRentalRequest(rentalRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsLoggedUserReserved")
    public ResponseEntity<?> getAllRentalRequestsLoggedUserReserved(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsLoggedUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("RESERVED")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsLoggedUserPaid")
    public ResponseEntity<?> getAllRentalRequestsLoggedUserPaid(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsLoggedUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("PAID")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsLoggedUserPending")
    public ResponseEntity<?> getAllRentalRequestsLoggedUser(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsLoggedUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("PENDING")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")//Kada se plati, svi zahtevi za isti auto u isto vreme ce biti odbijeni
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsLoggedUserCanceled")
    public ResponseEntity<?> getAllRentalRequestsLoggedUserCanceled(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsLoggedUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("CANCELED")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserReserved")
    public ResponseEntity<?> getAllRentalRequestsForUserReserved(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("RESERVED")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserPending")
    public ResponseEntity<?> getAllRentalRequestsForUserPending(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("PENDING")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserPaid")
    public ResponseEntity<?> getAllRentalRequestsForUserPaid(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("PAID")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserCanceled")
    public ResponseEntity<?> getAllRentalRequestsForUserCanceled(@RequestBody RentalRequestsLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("CANCELED")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/checkRentalRating")
    public ResponseEntity<?> checkRentalRating(@RequestBody RentalCheckDTO rentalCheckDTO) {
        return new ResponseEntity<>(this.rentalRequestService.checkRentalRating(rentalCheckDTO), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addRatingCarRequest")
    public ResponseEntity<?> addRatingCarRequest(@RequestBody RatingCarRequestDTO ratingCarRequestDTO) {
        return new ResponseEntity<>(this.ratingRequestService.addRatingCarRequest(ratingCarRequestDTO) ,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/getAllRatingRequest")
    public ResponseEntity<?> getAllRatingRequest() {
        return new ResponseEntity<>(this.ratingRequestService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/acceptRatingRequest")
    public ResponseEntity<?> acceptRatingRequest(@RequestBody RatingRequestAcceptDeclineDTO ratingRequestAcceptDeclineDTO) {
        this.ratingRequestService.acceptRatingRequest(ratingRequestAcceptDeclineDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/declineRatingRequest")
    public ResponseEntity<?> declineRatingRequest(@RequestBody RatingRequestAcceptDeclineDTO ratingRequestAcceptDeclineDTO) {
        this.ratingRequestService.declineRatingRequest(ratingRequestAcceptDeclineDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addCommentCarRequest")
    public ResponseEntity<?> addCommentCarRequest(@RequestBody CommentCarRequestDTO commentCarRequestDTO) {
        return new ResponseEntity<>(this.commentRequestService.addCommentCarRequest(commentCarRequestDTO) ,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/getAllCommentRequest")
    public ResponseEntity<?> getAllCommentRequest() {
        return new ResponseEntity<>(this.commentRequestService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/acceptCommentRequest")
    public ResponseEntity<?> acceptCommentRequest(@RequestBody CommentRequestAcceptDeclineDTO commentRequestAcceptDeclineDTO) {
        this.commentRequestService.acceptCommentRequest(commentRequestAcceptDeclineDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/declineCommentRequest")
    public ResponseEntity<?> declineCommentRequest(@RequestBody CommentRequestAcceptDeclineDTO commentRequestAcceptDeclineDTO) {
        this.commentRequestService.declineCommentRequest(commentRequestAcceptDeclineDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllCommentByCarId")
    public ResponseEntity<?> getAllCommentByCarId(@RequestBody CommentCarIdDTO commentCarIdDTO) {
        return new ResponseEntity<>(this.commentService.findAllByCarId(commentCarIdDTO.getCarId()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/aclSecurity")
    public ResponseEntity<?> aclSecurity(@RequestBody AclDTO aclDTO) throws Exception {
        this.acl.addRestorePermissionsAcl(aclDTO.getRole());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/aclSecurityLogout")
    public ResponseEntity<?> aclSecurityLogout() throws Exception {
        this.acl.addRestorePermissionsAcl("USER");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
