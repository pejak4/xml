package com.example.imageservice.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.imageservice.dto.AclDTO;
import com.example.imageservice.security.Acl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private Acl acl;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/uploadImage")
    public ResponseEntity<?> uploadImage() throws IOException {
        return new ResponseEntity<>(cloudinary.uploader().upload(new File("C:/Users/Stefan/Desktop/logo.png"),
                                    ObjectUtils.emptyMap()), HttpStatus.OK);
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
