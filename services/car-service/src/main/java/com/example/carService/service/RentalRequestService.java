package com.example.carService.service;

import com.example.carService.dto.*;
import com.example.carService.model.Car;
import com.example.carService.model.CarRentalDate;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.repository.CarRepository;
import com.example.carService.repository.RentalRequestRepository;
import com.example.carService.model.RentalRequestRole;
import com.soapserveryt.api.soap.ClientRequestRentalRequest;
import com.soapserveryt.api.soap.ServerRespond;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class RentalRequestService {

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public List<CarRentalRequest> getAllCarRentalRequest() { return rentalRequestRepository.findAll();}

    public CarRentalRequest saveRentalRequest(RentalRequestDTO rentalRequest) {
        Car c = this.carRepository.findOneById(rentalRequest.getCarId());

        CarRentalRequest crr = CarRentalRequest.builder().rentalRequestCar(c).startDate(rentalRequest.getStartData())
        .endDate(rentalRequest.getEndData()).createDate(new Timestamp(System.currentTimeMillis())).
                        userId(rentalRequest.getUserId()).forUserId(c.getUserId()).role(RentalRequestRole.valueOf("PENDING")).build();

        return this.rentalRequestRepository.save(crr);
    }

    public CarRentalRequest sentSoapRentalRequest(ClientRequestRentalRequest rentalRequest) {
        Car c = this.carRepository.findOneById(Long.parseLong(rentalRequest.getCarId()));

        Instant instant1 = Instant.parse(rentalRequest.getStartDate());
        Instant instant2 = Instant.parse(rentalRequest.getEndDate());

        Date startDate = Date.from(instant1);
        Date endDate = Date.from(instant2);

        Timestamp tss = new Timestamp(startDate.getTime());
        Timestamp tse = new Timestamp(endDate.getTime());

        CarRentalRequest crr = CarRentalRequest.builder().rentalRequestCar(c).startDate(tss)
                .endDate(tse).createDate(new Timestamp(System.currentTimeMillis())).agent(true).
                        userId(rentalRequest.getUserId()).forUserId("0").role(RentalRequestRole.valueOf("PENDING")).build();

        CarRentalRequest crr1 = this.rentalRequestRepository.save(crr);

        rentalRequest.setId(crr.getId());

        template = new WebServiceTemplate(marshaller);
        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8080/ws", rentalRequest);

        return this.rentalRequestRepository.save(crr);
    }

    public List<CarRentalRequest> findAllByUserIdAndCar(RentalRequestIdAndCarDTO data) {
        String user_id = data.getUserId();
        Long car_id = Long.parseLong(data.getCarId());
        return this.rentalRequestRepository.findAllByUserIdAndRentalRequestCarId(user_id, car_id);
    }

    public CarRentalRequest findOneById(Long id) {return this.rentalRequestRepository.findOneById(id);}

    public void declineRentalRequest(Long id) {
        CarRentalRequest crc = this.rentalRequestRepository.findOneById(id);
        crc.setRole(RentalRequestRole.valueOf("CANCELED"));

        this.rentalRequestRepository.save(crc);
    }

    public void acceptRentalRequest(Long id) {
        CarRentalRequest crc = this.rentalRequestRepository.findOneById(id);
        crc.setRole(RentalRequestRole.valueOf("RESERVED"));
        crc.setCreateDate(new Timestamp(System.currentTimeMillis()));

        this.rentalRequestRepository.save(crc);
    }

    @Scheduled(fixedRate=2000000) //Oko 30.3 minuta ce se pozicati ova metoda
    public void setRoleOfRentalRequest(){
        System.out.println("do some task");

        Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme

        //Svi zahtevi ako nakon 12 od kako su postali rezervisani, ne predju u stanje PAID tj ne budu placeni, odbijaju se
        List<CarRentalRequest> allRentalRequestReserved = this.rentalRequestRepository.findAllByRole(RentalRequestRole.valueOf("RESERVED"));
        for(CarRentalRequest crr : allRentalRequestReserved) {
            Timestamp timeCrr = crr.getCreateDate();
            timeCrr.setTime(timeCrr.getTime() + 12*1000*60*60);

            if(tm.after(timeCrr)) {
                crr.setRole(RentalRequestRole.valueOf("CANCELED"));
                this.rentalRequestRepository.save(crr);
            }
        }

        //Svi novokreirani su pending. AKo nakon 24 sata ne budu obradjeni postaju CANCELED tj odbijeni.
        List<CarRentalRequest> allRentalRequestPending = this.rentalRequestRepository.findAllByRole(RentalRequestRole.valueOf("PENDING"));
        for(CarRentalRequest crr : allRentalRequestPending) {
            Timestamp timeCrr = crr.getCreateDate();
            timeCrr.setTime(timeCrr.getTime() + 24*1000*60*60);

            if(tm.after(timeCrr)) {
                crr.setRole(RentalRequestRole.valueOf("CANCELED"));
                this.rentalRequestRepository.save(crr);
            }
        }
    }

    public void declineRentalRequestWhenPaid(RentalRequestDTO rentalRequestDTO) {
        //Pronadjemo sve oglase ka tom autu koji su u stanju PENDING i odbijemo ih, tj prebacimo ih u stanje CANCELED
        List<CarRentalRequest> crr = this.rentalRequestRepository.findRentalRequestWhenPaid(
                rentalRequestDTO.getStartData(), rentalRequestDTO.getEndData(), "PENDING",
                rentalRequestDTO.getUserId(), rentalRequestDTO.getCarId());

        for(CarRentalRequest c : crr) {
            c.setRole(RentalRequestRole.valueOf("CANCELED"));
            this.rentalRequestRepository.save(c);
        }
    }

    public Boolean ifHaveReservedRentalRequest(RentalRequestIfHaveReservedDTO rentalRequestDTO) {
        CarRentalRequest request = this.rentalRequestRepository.findOneById(Long.parseLong(rentalRequestDTO.getRentalRequestId()));

        List<CarRentalRequest> crr = this.rentalRequestRepository.findRentalRequestWhenPaid(
                rentalRequestDTO.getStartData(), rentalRequestDTO.getEndData(), "RESERVED",
                rentalRequestDTO.getForUserId(), request.getRentalRequestCar().getId());

        if(crr.size() != 0) {
            return true;
        }
        return false;
    }

    public List<CarRentalRequest> rentalRequestsLoggedUserIdAndRole(String userId, RentalRequestRole role) {
        List<CarRentalRequest> cs = this.rentalRequestRepository.findAllByUserIdAndRole(userId, role);
        for(CarRentalRequest crr : cs) {
            Car c = crr.getRentalRequestCar();
            c.setTransmission(StringEscapeUtils.escapeHtml4(c.getTransmission()));
            c.setFuelType(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setClassCar(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setModel(StringEscapeUtils.escapeHtml4(c.getModel()));
            c.setBrand(StringEscapeUtils.escapeHtml4(c.getBrand()));
            c.setDescription(StringEscapeUtils.escapeHtml4(c.getDescription()));
            c.setCityLocation(StringEscapeUtils.escapeHtml4(c.getCityLocation()));
            crr.setRentalRequestCar(c);
        }
        return cs;
    }

    public List<CarRentalRequest> rentalRequestsForUserIdAndRole(String forUserId, RentalRequestRole role) {
        List<CarRentalRequest> cs = this.rentalRequestRepository.findAllByForUserIdAndRoleAndAgent(forUserId, role, false);
        for(CarRentalRequest crr : cs) {
            Car c = crr.getRentalRequestCar();
            c.setTransmission(StringEscapeUtils.escapeHtml4(c.getTransmission()));
            c.setFuelType(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setClassCar(StringEscapeUtils.escapeHtml4(c.getFuelType()));
            c.setModel(StringEscapeUtils.escapeHtml4(c.getModel()));
            c.setBrand(StringEscapeUtils.escapeHtml4(c.getBrand()));
            c.setDescription(StringEscapeUtils.escapeHtml4(c.getDescription()));
            c.setCityLocation(StringEscapeUtils.escapeHtml4(c.getCityLocation()));
            crr.setRentalRequestCar(c);
        }
        return cs;
    }

    public CarRentalRequest setRolePaidRentalRequest(Long id) {
        CarRentalRequest crr = this.rentalRequestRepository.findOneById(id);
        crr.setRole(RentalRequestRole.valueOf("PAID"));

        //Pogledati posle zasto ne radi. Cilj je bio promeniti role i na drugoj strani.
        ClientRequestRentalRequest c = new ClientRequestRentalRequest();
        c.setCarId(crr.getRentalRequestCar().getId().toString());
        c.setEndDate(crr.getEndDate().toString());
        c.setId(crr.getId()); //Na drugoh strani je ovo secondId
        c.setStartDate(crr.getStartDate().toString());
        template = new WebServiceTemplate(marshaller);
        ServerRespond serverRespond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8080/ws", c);

        this.rentalRequestRepository.save(crr);
        return crr;
    }

    public Boolean checkRentalRating(RentalCheckDTO rentalCheckDTO) {
        Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme

        List<CarRentalRequest> crd = this.rentalRequestRepository.findAllRentalFrom(rentalCheckDTO.getFromUserId().toString()
                ,tm, rentalCheckDTO.getCarId());

        if(crd.size() != 0) {
            return true;
        }
        return false;
    }

}
