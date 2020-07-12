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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class ImageController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private Acl acl;
//    consumes = { "multipart/form-data", MediaType.APPLICATION_JSON_VALUE }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Map upload = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "use_filename", "true",
                        "unique_filename", "true"));
        return new ResponseEntity<>(upload.get("url"), HttpStatus.OK);
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
