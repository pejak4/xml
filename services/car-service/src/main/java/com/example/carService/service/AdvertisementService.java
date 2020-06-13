package com.example.carService.service;

import com.example.carService.repository.AdvertisementRepository;
import com.example.carService.dto.AdvertisementDTO;
import com.example.carService.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Car save(AdvertisementDTO a) {
        Integer seatsForKids = a.getSeatsForKids() == "" ? 0 : Integer.parseInt(a.getSeatsForKids());
        Integer plannedMileage = a.getPlannedMilage() == "" ? 1000000 : Integer.parseInt(a.getPlannedMilage());

        Car car = Car.builder().rating(0).numOfRating(0).brand(a.getBrandCars()).model(a.getModelCars()).capacitySeats(Integer.parseInt(a.getSeats())).
                capacitySeatsForKids(seatsForKids).cityLocation(a.getCityLocation()).CDW(Boolean.parseBoolean(a.getCdw())).
                classCar(a.getClassCarCars()).cubicCapacity(Integer.parseInt(a.getCubicCapacity())).horsePower(Integer.parseInt(a.getHorsePower())).
                description(a.getDescription()).fuelTankCapacity(Integer.parseInt(a.getFullTankCapacity())).doors(Integer.parseInt(a.getDoors())).
                fuelType(a.getFuelTypeCars()).gps(Boolean.parseBoolean(a.getGps())).mileage(Integer.parseInt(a.getMileageCars())).
                plannedMileage(plannedMileage).price(Integer.parseInt(a.getPrice())).
                transmission(a.getTransmissionCars()).usb(Boolean.parseBoolean(a.getUsb())).image("car.jpg").userId(a.getUserId()).build();

        return this.advertisementRepository.save(car);
    }
}
