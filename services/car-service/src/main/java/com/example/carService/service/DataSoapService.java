package com.example.carService.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//import com.soapserveryt.api.soap.ClientRequest;
//import com.soapserveryt.api.soap.ServerRespond;
//import com.soapserveryt.domain.DataPrivate;
//import com.soapserveryt.domain.User;
//import com.soapserveryt.domain.UserData;
//import com.soapserveryt.repository.DataPrivateRepository;
//import com.soapserveryt.repository.UserDataRepository;
//import com.soapserveryt.repository.UserRepository;

import com.example.carService.model.*;
import com.example.carService.repository.CarRepository;
import com.example.carService.repository.CommentRepository;
import com.example.carService.repository.OccupancyRepository;
import com.example.carService.repository.RentalRequestRepository;
import com.soapserveryt.api.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DataSoap
 */
@Service
public class DataSoapService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OccupancyRepository occupancyRepository;

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private CommentRepository commentRepository;

    public ServerRespond checkData(ClientRequest a) {
        ServerRespond serverRespond = new ServerRespond();

        Car car = Car.builder().agent(true).rating(0).numOfRating(0).brand(a.getBrand()).model(a.getModel()).capacitySeats(Integer.parseInt(a.getCapacitySeats())).
                capacitySeatsForKids(Integer.parseInt(a.getCapacitySeatsForKids())).cityLocation(a.getCityLocation()).CDW(Boolean.parseBoolean(a.getCDW())).
                classCar(a.getClassCar()).cubicCapacity(Integer.parseInt(a.getCubicCapacity())).horsePower(Integer.parseInt(a.getHorsePower())).
                description(a.getDescripton()).fuelTankCapacity(Integer.parseInt(a.getFuelTankCapacity())).doors(Integer.parseInt(a.getDoors())).
                fuelType(a.getFuelType()).gps(Boolean.parseBoolean(a.getGps())).mileage(Integer.parseInt(a.getMileage())).
                plannedMileage(Integer.parseInt(a.getPlannedMileage())).price(Integer.parseInt(a.getPrice())).
                transmission(a.getTransmission()).usb(Boolean.parseBoolean(a.getUsb())).image("car.jpg").userId(a.getUserId()).build();

        this.carRepository.save(car);

        serverRespond.setId(car.getId());
        return serverRespond;
    }

    public ServerRespond addOccupancyReturnId(ClirentRequestOccupancy cro) throws ParseException {
        ServerRespond serverRespond = new ServerRespond();

        Car c = carRepository.findOneById(Long.parseLong(cro.getCarId()));

        Instant instant1 = Instant.parse(cro.getStartDate());
        Instant instant2 = Instant.parse(cro.getEndDate());

        Date startDate = Date.from(instant1);
        Date endDate = Date.from(instant2);

        Timestamp tss = new Timestamp(startDate.getTime());
        Timestamp tse = new Timestamp(endDate.getTime());

        Occupancy o = Occupancy.builder().startDate(tss).endDate(tse).occupancyCar(c).build();

        this.occupancyRepository.save(o);

        serverRespond.setId(o.getId());
        return serverRespond;
    }

    public void setRoleRentalRequest(ClientRequestRentalRequestId  crrri) { // Kada pritisnemo accept u agentkom delu, i hocemo da rezervisemo rental request
        CarRentalRequest crr = this.rentalRequestRepository.findOneById(crrri.getId());

        if(crrri.getType().equals("RESERVED")) {
            crr.setRole(RentalRequestRole.RESERVED);
            crr.setCreateDate(new Timestamp(System.currentTimeMillis()));
        } else if(crrri.getType().equals("CANCELED")) {
            crr.setRole(RentalRequestRole.CANCELED);
        }
        this.rentalRequestRepository.save(crr);
    }

    public ServerRespond addComment(ClientRequestComment request) {
        Comment com = Comment.builder().carId(request.getCarId()).descriptionComment(request.getDescriptionComment())
                .fromUserId(Long.parseLong("0")).build();

        this.commentRepository.save(com);

        ServerRespond response = new ServerRespond();
        response.setId(com.getId());

        return response;
    }

    public ClientRequestCars getAll(ClientRequestCars request) {
        List<Car> cars = this.carRepository.findAllByAgent(true);
        ClientRequestCars clientRequestCars = new ClientRequestCars();
        ClientRequestCar clientRequestCar = new ClientRequestCar();

        for(Car c : cars) {
            clientRequestCar.setBrand(c.getBrand());
            clientRequestCar.setCapacitySeats(String.valueOf(c.getCapacitySeats()));
            clientRequestCar.setCapacitySeatsForKids(String.valueOf(c.getCapacitySeatsForKids()));
            clientRequestCar.setCDW(String.valueOf(c.getCDW()));
            clientRequestCar.setCityLocation(c.getCityLocation());
            clientRequestCar.setClassCar(c.getClassCar());
            clientRequestCar.setCubicCapacity(String.valueOf(c.getCubicCapacity()));
            clientRequestCar.setDescripton(c.getDescription());
            clientRequestCar.setDoors(String.valueOf(c.getDoors()));
            clientRequestCar.setFuelTankCapacity(String.valueOf(c.getFuelTankCapacity()));
            clientRequestCar.setFuelType(c.getFuelType());
            clientRequestCar.setGps(String.valueOf(c.getGps()));
            clientRequestCar.setHorsePower(String.valueOf(c.getHorsePower()));
            clientRequestCar.setId(c.getId());
            clientRequestCar.setImage(c.getImage());
            clientRequestCar.setMileage(String.valueOf(c.getMileage()));
            clientRequestCar.setModel(c.getModel());
            clientRequestCar.setPlannedMileage(String.valueOf(c.getPlannedMileage()));
            clientRequestCar.setPrice(String.valueOf(c.getPrice()));
            clientRequestCar.setTransmission(c.getTransmission());
            clientRequestCar.setUsb(String.valueOf(c.getUsb()));
            clientRequestCar.setUserId(String.valueOf(c.getUserId()));
            clientRequestCar.setRating(c.getRating());

            clientRequestCars.getNewsItems().add(clientRequestCar);
        }

        return clientRequestCars;
    }

    public void setMileageAndDescription(ClientRequestSetMileageAndDescription request) {
        Car c = this.carRepository.findOneById(request.getCarId());
        c.setMileage(request.getMileage());
        c.setDescription(request.getDescription());
        this.carRepository.save(c);
    }
}