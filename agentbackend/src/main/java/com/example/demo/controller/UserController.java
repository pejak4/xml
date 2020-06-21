package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.RentalRequestRole;
import com.example.demo.model.Users;
import com.example.demo.service.*;
import com.example.demo.view.UserLoginView;
import com.example.demo.view.UserRegisterView;
import com.example.demo.view.UserTokenState;
import com.soapclient.api.domain.ClientRequest;
import com.soapclient.api.domain.ClientRequestComment;
import com.soapclient.api.domain.ClientRequestSetMileageAndDescription;
import com.soapclient.api.domain.ClirentRequestOccupancy;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private OccupancyService occupancyService;

    @Autowired
    private RentalRequestService rentalRequestService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RentalService rentalService;


    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public ResponseEntity<UserTokenState> login(@RequestBody UserLoginView user) throws  NotFoundException {
        return new ResponseEntity<>(this.userService.login(user), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/registration")
    public ResponseEntity<?> register(@RequestBody UserRegisterView userRegisterView) {
        return new ResponseEntity<>(this.userService.register(userRegisterView), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/getLoggedUser")
    public ResponseEntity<?> getLoggedUserId() {
        Users u = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addAdvertisement")
    public ResponseEntity<?> addAdvertisement(@RequestBody ClientRequest car) {
        return new ResponseEntity<>(this.advertisementService.save(car), HttpStatus.CREATED);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/api")
//    public ResponseEntity<?> respond(@RequestBody ClientRequest request) {
//        return new ResponseEntity<>(client.respond(request), HttpStatus.OK);
//    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsLogedUser")
    public ResponseEntity<?> getCarsLogedUser(@RequestBody UserIdDTO id) {
        return new ResponseEntity<>(this.advertisementService.findAllByUserId(id.getUserId()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addOccupancy")
    public ResponseEntity<?> addOccupancy(@RequestBody ClirentRequestOccupancy o) throws ParseException {
        this.occupancyService.saveOccupancy(o);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserReserved")
    public ResponseEntity<?> getAllRentalRequestsForUserReserved(@RequestBody RentalRequestLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("RESERVED")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserPending")
    public ResponseEntity<?> getAllRentalRequestsForUserPending(@RequestBody RentalRequestLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("PENDING")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserPaid")
    public ResponseEntity<?> getAllRentalRequestsForUserPaid(@RequestBody RentalRequestLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("PAID")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllRentalRequestsForUserCanceled")
    public ResponseEntity<?> getAllRentalRequestsForUserCanceled(@RequestBody RentalRequestLoggedUserDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.rentalRequestsForUserIdAndRole(rentalRequest.getUserId(), RentalRequestRole.valueOf("CANCELED")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")//ako je taj auto vec rezervisan u tom periodu
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/ifHaveReservedRentalRequest")
    public ResponseEntity<?> ifHaveReservedRentalRequest(@RequestBody RentalRequestIfHaveReservedDTO rentalRequest) {
        return new ResponseEntity<>(this.rentalRequestService.ifHaveReservedRentalRequest(rentalRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/acceptRentalRequest")
    public ResponseEntity<?> acceptRentalRequest(@RequestBody RentalRequestAcceptDeclineDTO r) {
        this.rentalRequestService.acceptRentalRequest(Long.parseLong(r.getRentalRequestId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/declineRentalRequest")
    public ResponseEntity<?> declineRentalRequest(@RequestBody RentalRequestAcceptDeclineDTO r) {
        this.rentalRequestService.declineRentalRequest(Long.parseLong(r.getRentalRequestId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getAllCommentByCarId")
    public ResponseEntity<?> getAllCommentByCarId(@RequestBody CommentCarIdDTO commentCarIdDTO) {
        return new ResponseEntity<>(this.commentService.findAllByCarId(commentCarIdDTO.getCarId()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping(path = "/getSingleCar")
    public ResponseEntity<?> getSingleCar(@RequestParam Long id) {
        return new ResponseEntity<>(this.advertisementService.findOneCar(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/addComment")
    public ResponseEntity<?> addComment(@RequestBody ClientRequestComment commentAddDTO) {
        this.commentService.save(commentAddDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001") // Pronalazimo sva auta prijavljenog korisnika koja su bila rentana a nikada nije upisana kilometraza
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getCarsRentalEnd")
    public ResponseEntity<?> getCarsRentalEnd(@RequestBody RentalEndDTO rentalEndDTO) {
        return new ResponseEntity<>(this.rentalRequestService.findAllCarInRentalEnd(rentalEndDTO), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/setMileageAndDescription")
    public ResponseEntity<?> setMileageAndDescription(@RequestBody ClientRequestSetMileageAndDescription request) {
        this.advertisementService.setMileageAndDescription(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/getCountComments")
    public ResponseEntity<?> getCountComments(@RequestBody UserIdDTO id) {
        return new ResponseEntity<>(this.commentService.getCountComment(id), HttpStatus.OK);
    }


    }