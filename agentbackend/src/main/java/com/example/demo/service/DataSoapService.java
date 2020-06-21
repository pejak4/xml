package com.example.demo.service;


import com.example.demo.model.Car;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.model.RentalRequestRole;
import com.example.demo.repository.AdvertisementRepository;
import com.example.demo.repository.RentalRequestRepository;
import com.soapclient.api.domain.ClientRequestRentalRequest;
import com.soapclient.api.domain.ServerRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * DataSoap
 */
@Service
public class DataSoapService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    public ServerRespond clientRentalRequest(ClientRequestRentalRequest a) {
        ServerRespond serverRespond = new ServerRespond();

        Instant instant1 = Instant.parse(a.getStartDate());
        Instant instant2 = Instant.parse(a.getEndDate());

        Date startDate = Date.from(instant1);
        Date endDate = Date.from(instant2);

        Timestamp tss = new Timestamp(startDate.getTime());
        Timestamp tse = new Timestamp(endDate.getTime());

        Car c = advertisementRepository.findOneById(Long.parseLong(a.getCarId()));

        Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme

        CarRentalRequest crr = CarRentalRequest.builder().role(RentalRequestRole.PENDING).
                startDate(tss).endDate(tse).createDate(tm).forUserId(c.getUserId()).
                userId(a.getUserId()).rentalRequestCar(c).secondId(a.getId()).build();

        this.rentalRequestRepository.save(crr);
        serverRespond.setId(crr.getId());

        return serverRespond;
    }

}