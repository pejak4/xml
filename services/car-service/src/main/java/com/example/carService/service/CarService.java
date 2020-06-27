package com.example.carService.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import com.example.carService.model.Car;
import com.example.carService.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findCarSearch(Timestamp startDate, Timestamp endDate, String cityLocation){
        List<Car> cars = this.carRepository.findCarSearch(startDate, endDate, cityLocation);
        for(Car c : cars) {
            c.setDescription(StringEscapeUtils.escapeHtml4(c.getDescription()));
            c.setBrand(StringEscapeUtils.escapeHtml4(c.getBrand()));
            c.setCityLocation(StringEscapeUtils.escapeHtml4(c.getCityLocation()));
            c.setModel(StringEscapeUtils.escapeHtml4(c.getModel()));
            c.setClassCar(StringEscapeUtils.escapeHtml4(c.getClassCar()));
            c.setFuelType(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setTransmission(StringEscapeUtils.escapeHtml4(c.getTransmission()));
        }

        log.info("Client is searching cars in city: " + cityLocation);
        return cars;
    }

    public List<Car> findCarFilter(String brand, String model, String fuel_type, String transmission, String class_car,
                                   Double min_price, Double max_price, String mileageS, String planned_mileageS,
                                   Boolean CDW, String capacity_seats_for_kidsS, String city_location,
                                   Timestamp start_date, Timestamp end_date) {

        Integer mileage = mileageS.equals("") ? 0 : Integer.parseInt(mileageS);
        Integer planned_mileage = planned_mileageS.equals("") ? 0 : Integer.parseInt(planned_mileageS);
        Integer capacity_seats_for_kids = capacity_seats_for_kidsS.equals("") ? 0 : Integer.parseInt(capacity_seats_for_kidsS);

        List<Long> carsId = this.carRepository.findCarFilter(brand, model, fuel_type, transmission, class_car, min_price,
                                                max_price, mileage, planned_mileage, CDW, capacity_seats_for_kids,
                                                city_location, start_date, end_date);

        List<Car> cars = this.carRepository.findAllById(carsId);
        for(Car c : cars) {
            c.setDescription(StringEscapeUtils.escapeHtml4(c.getDescription()));
            c.setBrand(StringEscapeUtils.escapeHtml4(c.getBrand()));
            c.setCityLocation(StringEscapeUtils.escapeHtml4(c.getCityLocation()));
            c.setModel(StringEscapeUtils.escapeHtml4(c.getModel()));
            c.setClassCar(StringEscapeUtils.escapeHtml4(c.getClassCar()));
            c.setFuelType(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setTransmission(StringEscapeUtils.escapeHtml4(c.getTransmission()));
        }

        log.info("Client is filtering search");
        return cars;
    }

    public List<Car> findAll() { return this.carRepository.findAll();}

    public List<Car> findAllByUserId(String user_id) {
        List<Car> cars = this.carRepository.findAllByUserId(user_id);
        for(Car c : cars) {
            c.setDescription(StringEscapeUtils.escapeHtml4(c.getDescription()));
            c.setBrand(StringEscapeUtils.escapeHtml4(c.getBrand()));
            c.setCityLocation(StringEscapeUtils.escapeHtml4(c.getCityLocation()));
            c.setModel(StringEscapeUtils.escapeHtml4(c.getModel()));
            c.setClassCar(StringEscapeUtils.escapeHtml4(c.getClassCar()));
            c.setFuelType(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setTransmission(StringEscapeUtils.escapeHtml4(c.getTransmission()));
        }
        return cars;
    }
    public Optional<Car> findOneCar(Long id) {
        return this.carRepository.findById(id);
    }
}
