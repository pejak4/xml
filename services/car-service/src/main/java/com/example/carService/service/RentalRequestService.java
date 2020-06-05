package com.example.carService.service;

import com.example.carService.dto.CarFilterDTO;
import com.example.carService.dto.RentalRequestDTO;
import com.example.carService.dto.RentalRequestIdAndCarDTO;
import com.example.carService.model.Car;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.repository.RentalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalRequestService {

    @Autowired
    private RentalRequestRepository rentalRequestRepository;


    public List<CarRentalRequest> getAllCarRentalRequest() { return rentalRequestRepository.findAll();}

    public CarRentalRequest saveRentalRequest(RentalRequestDTO rentalRequest) {
        CarRentalRequest crr = CarRentalRequest.builder().rentalRequestCar(rentalRequest.getCar()).startDate(rentalRequest.getStartData())
        .endDate(rentalRequest.getEndData()).userId(rentalRequest.getUserId()).build();

        return this.rentalRequestRepository.save(crr);
    }

    public CarRentalRequest findOneByUserIdAndCar(RentalRequestIdAndCarDTO data) {
        String user_id = data.getUserId();
        Long car_id = Long.parseLong(data.getCarId());
        return this.rentalRequestRepository.findOneByUserIdAndRentalRequestCarId(user_id, car_id);
    }

    public CarRentalRequest findOneById(Long id) {return this.rentalRequestRepository.findOneById(id);}
    public void deleteRentalRequest(Long id) {this.rentalRequestRepository.deleteRentalRequest(id);}
}
