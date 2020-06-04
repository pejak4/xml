package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public ResponseEntity<UserTokenState> login(@RequestBody UserLoginDTO user) throws  NotFoundException {
        return new ResponseEntity<>(this.userService.login(user), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/registration")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userRegisterView) {
        return new ResponseEntity<>(this.userService.register(userRegisterView), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody UserCheckEmailDTO user){
        return new ResponseEntity<>(this.userService.CheckEmail(user.getEmail()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/setStateUser")
    public ResponseEntity<?> setStateUser(@RequestBody UserSetStateDTO userId) {
        return new ResponseEntity<>(this.userService.setStateUser(userId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/getAllUsersByEnabled")
    public ResponseEntity<?> getAllUsersByEnabled(@RequestBody UserEnabledDTO enabled) {
        return new ResponseEntity<>(this.userService.findAllByEnabled(enabled.getEnabled()), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path= "/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteDTO userId) {
        return new ResponseEntity<>(this.userService.deleteUser(userId), HttpStatus.OK);
    }

}