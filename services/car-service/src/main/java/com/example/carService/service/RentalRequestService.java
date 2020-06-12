package com.example.carService.service;

import com.example.carService.dto.CarFilterDTO;
import com.example.carService.dto.RentalRequestDTO;
import com.example.carService.dto.RentalRequestIdAndCarDTO;
import com.example.carService.dto.RentalRequestIfHaveReservedDTO;
import com.example.carService.model.Car;
import com.example.carService.model.CarRentalRequest;
import com.example.carService.repository.CarRepository;
import com.example.carService.repository.RentalRequestRepository;
import com.example.carService.model.RentalRequestRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RentalRequestService {

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private CarRepository carRepository;

    public List<CarRentalRequest> getAllCarRentalRequest() { return rentalRequestRepository.findAll();}

    public CarRentalRequest saveRentalRequest(RentalRequestDTO rentalRequest) {
        Car c = this.carRepository.findOneById(rentalRequest.getCarId());

        CarRentalRequest crr = CarRentalRequest.builder().rentalRequestCar(c).startDate(rentalRequest.getStartData())
        .endDate(rentalRequest.getEndData()).createDate(new Timestamp(System.currentTimeMillis())).
                        userId(rentalRequest.getUserId()).forUserId(c.getUserId()).role(RentalRequestRole.valueOf("PENDING")).build();

        return this.rentalRequestRepository.save(crr);
    }

    public CarRentalRequest findOneByUserIdAndCar(RentalRequestIdAndCarDTO data) {
        String user_id = data.getUserId();
        Long car_id = Long.parseLong(data.getCarId());
        return this.rentalRequestRepository.findOneByUserIdAndRentalRequestCarId(user_id, car_id);
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
        return this.rentalRequestRepository.findAllByUserIdAndRole(userId, role);
    }

    public List<CarRentalRequest> rentalRequestsForUserIdAndRole(String forUserId, RentalRequestRole role) {
        return this.rentalRequestRepository.findAllByForUserIdAndRole(forUserId, role);
    }

    public CarRentalRequest setRolePaidRentalRequest(Long id) {
        CarRentalRequest crr = this.rentalRequestRepository.findOneById(id);
        crr.setRole(RentalRequestRole.valueOf("PAID"));

        this.rentalRequestRepository.save(crr);
        return crr;
    }

}
