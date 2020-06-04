package com.example.codebook.controller;

import com.example.codebook.Service.BrandService;
import com.example.codebook.Service.FuelTypeService;
import com.example.codebook.Service.ModelService;
import com.example.codebook.Service.TransmissionService;
import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.dto.ModelDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CodebookController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private TransmissionService transmissionService;

    @Autowired
    private FuelTypeService fuelTypeService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllBrand")
    public ResponseEntity<?> getAllBrand() {
        return new ResponseEntity<>(this.brandService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllModels")
    public ResponseEntity<?> getAllModels() {
        return new ResponseEntity<>(this.modelService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllTransmissions")
    public ResponseEntity<?> getAllTransmissions() {
        return new ResponseEntity<>(this.transmissionService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/getAllFuelTypes")
    public ResponseEntity<?> getAllFuelTypes() {
        return new ResponseEntity<>(this.fuelTypeService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/deleteBrand")
    public ResponseEntity<?> deleteBrand(@RequestBody CodebookDTO brand) {
        this.brandService.deleteBrand(Long.parseLong(brand.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/deleteModel")
    public ResponseEntity<?> deleteModel(@RequestBody CodebookDTO model) {
        this.modelService.deleteModel(Long.parseLong(model.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/deleteTransmission")
    public ResponseEntity<?> deleteTransmission(@RequestBody CodebookDTO transmission) {
        this.transmissionService.deleteTransmission(Long.parseLong(transmission.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/deleteFuelType")
    public ResponseEntity<?> deleteFuelType(@RequestBody CodebookDTO fuelType) {
        this.fuelTypeService.deleteFuelType(Long.parseLong(fuelType.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/createNewBrand")
    public ResponseEntity<?> createNewBrand(@RequestBody CodebookDTO brand) {
        return new ResponseEntity<>(this.brandService.save(brand), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/createNewFuelType")
    public ResponseEntity<?> createNewFuelType(@RequestBody CodebookDTO fuelType) {
        return new ResponseEntity<>(this.fuelTypeService.save(fuelType), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/createNewTransmission")
    public ResponseEntity<?> createNewTransmission(@RequestBody CodebookDTO transmission) {
        return new ResponseEntity<>(this.transmissionService.save(transmission), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/createNewModel")
    public ResponseEntity<?> createNewModel(@RequestBody ModelDTO model) {
        return new ResponseEntity<>(this.modelService.save(model), HttpStatus.OK);
    }
}
