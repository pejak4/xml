package com.example.demo.service;

import com.example.demo.dto.RentalEndDTO;
import com.example.demo.model.CarRentalDate;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public void addRental(CarRentalRequest rr) {
        CarRentalDate crd = CarRentalDate.builder().car(rr.getRentalRequestCar()).startDate(rr.getStartDate())
                .endDate(rr.getEndDate()).fromUserId(Long.parseLong(rr.getUserId())).build();

        this.rentalRepository.save(crd);
    }

}
