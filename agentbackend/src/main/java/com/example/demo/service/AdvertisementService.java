package com.example.demo.service;


import com.example.demo.dto.AdvertisementDTO;
import com.example.demo.model.Car;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.repository.AdvertisementRepository;
import com.example.demo.repository.RentalRequestRepository;
import com.soapclient.api.domain.ClientRequest;
import com.soapclient.api.domain.ClientRequestSetMileageAndDescription;
import com.soapclient.api.domain.ServerRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import java.util.List;


@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;


    public Car save(ClientRequest a) {
        Integer seatsForKids = a.getCapacitySeatsForKids() == "" ? 0 : Integer.parseInt(a.getCapacitySeatsForKids());
        Integer plannedMileage = a.getPlannedMileage() == "" ? 1000000 : Integer.parseInt(a.getPlannedMileage());

        Car car = Car.builder().rating(0).numOfRating(0).brand(a.getBrand()).model(a.getModel()).capacitySeats(Integer.parseInt(a.getCapacitySeats())).
                capacitySeatsForKids(seatsForKids).cityLocation(a.getCityLocation()).CDW(Boolean.parseBoolean(a.getCDW())).
                classCar(a.getClassCar()).cubicCapacity(Integer.parseInt(a.getCubicCapacity())).horsePower(Integer.parseInt(a.getHorsePower())).
                description(a.getDescription()).fuelTankCapacity(Integer.parseInt(a.getFuelTankCapacity())).doors(Integer.parseInt(a.getDoors())).
                fuelType(a.getFuelType()).gps(Boolean.parseBoolean(a.getGps())).mileage(Integer.parseInt(a.getMileage())).
                plannedMileage(plannedMileage).price(Integer.parseInt(a.getPrice())).secondId(Long.parseLong("0")).
                transmission(a.getTransmission()).usb(Boolean.parseBoolean(a.getUsb())).image("car.jpg").userId(a.getUserId()).build();

        template = new WebServiceTemplate(marshaller);
        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8081/ws", a);

        car.setSecondId(respond.getId());

        return this.advertisementRepository.save(car);
    }

    public List<Car> findAllByUserId(String userId) {
        return this.advertisementRepository.findAllByUserId(userId);
    }
    public Car findOneCar(Long id) {
        return this.advertisementRepository.findOneById(id);
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

//    public void updateData() {
//        List<Car> carList = this.advertisementRepository.findAll();
//        List<ClientRequest> clientRequestsList;
//        ClientRequest clientRequest;
//        for(Car c : carList) {
//            clientRequest.
//        }
//    }



}
