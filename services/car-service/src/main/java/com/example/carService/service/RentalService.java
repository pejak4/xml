package com.example.carService.service;

import com.example.carService.dto.RentalRequestAcceptDeclineDTO;
import com.example.carService.dto.RentalRequestDTO;
import com.example.carService.model.CarRentalDate;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public void addRental(CarRentalRequest rr) {
        CarRentalDate crd = CarRentalDate.builder().car(rr.getRentalRequestCar()).startDate(rr.getStartDate())
                .endDate(rr.getEndDate()).build();

        this.rentalRepository.save(crd);
    }
}
