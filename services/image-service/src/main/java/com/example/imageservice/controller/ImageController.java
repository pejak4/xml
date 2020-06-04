package com.example.imageservice.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private Cloudinary cloudinary;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/uploadImage")
    public ResponseEntity<?> uploadImage() throws IOException {
        return new ResponseEntity<>(cloudinary.uploader().upload(new File("C:/Users/Stefan/Desktop/logo.png"),
                                    ObjectUtils.emptyMap()), HttpStatus.OK);
    }
}
