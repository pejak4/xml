package com.example.carService.service;

import com.example.carService.dto.RentalCheckDTO;
import com.example.carService.dto.RentalRequestAcceptDeclineDTO;
import com.example.carService.dto.RentalRequestDTO;
import com.example.carService.model.CarRentalDate;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

//    public void addRental(CarRentalRequest rr) {
//        CarRentalDate crd = CarRentalDate.builder().car(rr.getRentalRequestCar()).startDate(rr.getStartDate())
//                .endDate(rr.getEndDate()).fromUserId(Long.parseLong(rr.getUserId())).build();
//
//        this.rentalRepository.save(crd);
//    }

//    public Boolean checkRentalRating(RentalCheckDTO rentalCheckDTO) {
//        Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme
//
//        List<CarRentalDate> crd = this.rentalRepository.findAllRentalFrom(rentalCheckDTO.getFromUserId()
//        ,tm, rentalCheckDTO.getCarId());
//
//        if(crd.size() != 0) {
//            return true;
//        }
//        return false;
//    }


}
