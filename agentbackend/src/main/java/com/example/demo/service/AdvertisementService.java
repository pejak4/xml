package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.repository.AdvertisementRepository;
import com.example.demo.repository.RentalRequestRepository;
import com.soapclient.api.domain.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    @Autowired
    private PricelistService pricelistService;

    private WebServiceTemplate template;


    public Car save(ClientRequest a) {
        Integer seatsForKids = a.getCapacitySeatsForKids() == "" ? 0 : Integer.parseInt(a.getCapacitySeatsForKids());
        Integer plannedMileage = a.getPlannedMileage() == "" ? 1000000 : Integer.parseInt(a.getPlannedMileage());

        Car car = Car.builder().rating(0).numOfRating(0).brand(a.getBrand()).model(a.getModel()).capacitySeats(Integer.parseInt(a.getCapacitySeats())).
                capacitySeatsForKids(seatsForKids).cityLocation(a.getCityLocation()).CDW(Boolean.parseBoolean(a.getCDW())).
                classCar(a.getClassCar()).cubicCapacity(Integer.parseInt(a.getCubicCapacity())).horsePower(Integer.parseInt(a.getHorsePower())).
                description(a.getDescription()).fuelTankCapacity(Integer.parseInt(a.getFuelTankCapacity())).doors(Integer.parseInt(a.getDoors())).
                fuelType(a.getFuelType()).gps(Boolean.parseBoolean(a.getGps())).mileage(Integer.parseInt(a.getMileage())).
                plannedMileage(plannedMileage).price(Double.parseDouble(a.getPrice())).secondId(Long.parseLong("0")).
                transmission(a.getTransmission()).usb(Boolean.parseBoolean(a.getUsb())).image("car.jpg").userId(a.getUserId()).build();

        template = new WebServiceTemplate(marshaller);
        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8081/ws", a);

        car.setSecondId(respond.getId());

        return this.advertisementRepository.save(car);
    }

    public List<Car> findAllByUserId(String userId) {
        List<Car> cars = this.advertisementRepository.findAllByUserId(userId);
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
    public Car findOneCar(Long id) {
        Car c = this.advertisementRepository.findOneById(id);

        c.setDescription(StringEscapeUtils.escapeHtml4(c.getDescription()));
        c.setBrand(StringEscapeUtils.escapeHtml4(c.getBrand()));
        c.setCityLocation(StringEscapeUtils.escapeHtml4(c.getCityLocation()));
        c.setModel(StringEscapeUtils.escapeHtml4(c.getModel()));
        c.setClassCar(StringEscapeUtils.escapeHtml4(c.getClassCar()));
        c.setFuelType(StringEscapeUtils.escapeHtml4(c.getFuelType()));
        c.setTransmission(StringEscapeUtils.escapeHtml4(c.getTransmission()));

        return c;
    }

    public void setMileageAndDescription(ClientRequestSetMileageAndDescription request) {
        Car c = this.advertisementRepository.findOneById(request.getCarId());
        int mileage;
        mileage = c.getMileage();
        mileage = mileage + request.getMileage();
        c.setMileage(mileage);
        c.setDescription(request.getDescription());
        this.advertisementRepository.save(c);

        CarRentalRequest crr = this.rentalRequestRepository.findOneById(request.getRentalRequestId());
        crr.setCheck_mileage(true);
        this.rentalRequestRepository.save(crr);

        request.setCarId(c.getSecondId()); // Pre nego sto posaljemo na drugu stranu, menjamo id na Id na drugoj strani
        template = new WebServiceTemplate(marshaller);
        template.marshalSendAndReceive("http://localhost:8081/ws", request);
    }

    @Scheduled(fixedRate=2000000)
    public void updateData() {
        ClientRequestCars request = new ClientRequestCars();
        List<Car> cars = this.advertisementRepository.findAll();

        template = new WebServiceTemplate(marshaller);
        ClientRequestCars clientRequestCars = (ClientRequestCars) template.marshalSendAndReceive("http://localhost:8081/ws", request);

        for(Car c : cars) {
            for(ClientRequestCar clientCar : clientRequestCars.getNewsItems()) {
               if(c.getSecondId() == clientCar.getId()) {
                   c.setBrand(clientCar.getBrand());
                   c.setCapacitySeats(Integer.parseInt(clientCar.getCapacitySeats()));
                   c.setCapacitySeatsForKids(Integer.parseInt(clientCar.getCapacitySeatsForKids()));
                   c.setCDW(Boolean.parseBoolean(clientCar.getCDW()));
                   c.setCityLocation(clientCar.getCityLocation());
                   c.setClassCar(clientCar.getClassCar());
                   c.setCubicCapacity(Integer.parseInt(clientCar.getCubicCapacity()));
                   c.setDescription(clientCar.getDescripton());
                   c.setDoors(Integer.parseInt(clientCar.getDoors()));
                   c.setFuelTankCapacity(Double.parseDouble(clientCar.getFuelTankCapacity()));
                   c.setFuelType(clientCar.getFuelType());
                   c.setGps(Boolean.parseBoolean(clientCar.getGps()));
                   c.setHorsePower(Integer.parseInt(clientCar.getHorsePower()));
                   c.setId(clientCar.getId());
                   c.setImage(clientCar.getImage());
                   c.setMileage(Integer.parseInt(clientCar.getMileage()));
                   c.setModel(clientCar.getModel());
                   c.setPlannedMileage(Integer.parseInt(clientCar.getPlannedMileage()));
                   c.setPrice(Double.parseDouble(clientCar.getPrice()));
                   c.setTransmission(clientCar.getTransmission());
                   c.setUsb(Boolean.parseBoolean(clientCar.getUsb()));
                   c.setUserId(clientCar.getUserId());
                   c.setRating(clientCar.getRating());

                   this.advertisementRepository.save(c);
               }
            }
        }
    }

    @Scheduled(fixedRate=2000000)
    public void setDayInWeek() {
        List<Car> cars = this.advertisementRepository.findAll();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for(Car c : cars) {
            if(!c.getCDW()) {
                if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                    c.setPrice(c.getPricelist().getSunday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                    c.setPrice(c.getPricelist().getMonday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
                    c.setPrice(c.getPricelist().getTuesday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
                    c.setPrice(c.getPricelist().getWednesday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
                    c.setPrice(c.getPricelist().getThursday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
                    c.setPrice(c.getPricelist().getFriday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                    c.setPrice(c.getPricelist().getSaturday());
                }
            } else {
                if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                    c.setPrice(c.getPricelist().getSunday());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                    c.setPrice(c.getPricelist().getMonday() + c.getPricelist().getCdw());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
                    c.setPrice(c.getPricelist().getTuesday() + c.getPricelist().getCdw());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
                    c.setPrice(c.getPricelist().getWednesday() + c.getPricelist().getCdw());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
                    c.setPrice(c.getPricelist().getThursday() + c.getPricelist().getCdw());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
                    c.setPrice(c.getPricelist().getFriday() + c.getPricelist().getCdw());
                } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                    c.setPrice(c.getPricelist().getSaturday() + c.getPricelist().getCdw());
                }
            }
        }
        this.updateData();
    }



}
