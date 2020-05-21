package com.project.userservice.controller;

import com.project.userservice.common.TimeProvider;
import com.project.userservice.dto.UserCheckEmailDTO;
import com.project.userservice.dto.UserLoginDto;
import com.project.userservice.dto.UserRegistrationDto;
import com.project.userservice.security.TokenUtils;
import com.project.userservice.service.AuthorityService;
import com.project.userservice.service.CustomUserDetailsService;
import com.project.userservice.service.UserService;
import com.project.userservice.view.UserTokenState;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public ResponseEntity<UserTokenState> login(@RequestBody UserLoginDto user) throws NotFoundException {
        return new ResponseEntity<>(this.userService.userLogin(user), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDto user) {
        return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody UserCheckEmailDTO user){
        return new ResponseEntity<>(this.userService.checkEmail(user.getEmail()), HttpStatus.OK);
    }
}
