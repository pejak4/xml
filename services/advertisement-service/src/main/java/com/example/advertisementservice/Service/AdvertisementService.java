package com.example.advertisementservice.Service;

import com.example.advertisementservice.Repository.AdvertisementRepository;
import com.example.advertisementservice.dto.AdvertisementDTO;
import com.example.advertisementservice.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Car save(AdvertisementDTO a) {
        Car car = Car.builder().brand(a.getBrandCars()).model(a.getModelCars()).capacitySeats(Integer.parseInt(a.getSeats())).
                capacitySeatsForKids(Integer.parseInt(a.getSeatsForKids())).cityLocation(a.getCityLocation()).CDW(Boolean.parseBoolean(a.getCdw())).
                classCar(a.getClassCarCars()).cubicCapacity(Integer.parseInt(a.getCubicCapacity())).horsePower(Integer.parseInt(a.getHorsePower())).
                description(a.getDescription()).fuelTankCapacity(Integer.parseInt(a.getFullTankCapacity())).doors(Integer.parseInt(a.getDoors())).
                fuelType(a.getFuelTypeCars()).gps(Boolean.parseBoolean(a.getGps())).mileage(Integer.parseInt(a.getMileageCars())).
                plannedMileage(Integer.parseInt(a.getPlannedMilage())).price(Integer.parseInt(a.getPrice())).transmission(a.getTransmissionCars()).usb(Boolean.parseBoolean(a.getUsb())).build();

        return this.advertisementRepository.save(car);
    }
}
