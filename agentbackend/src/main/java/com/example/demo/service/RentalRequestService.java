package com.example.demo.service;

import com.example.demo.dto.RentalEndDTO;
import com.example.demo.dto.RentalRequestIfHaveReservedDTO;
import com.example.demo.model.CarRentalDate;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.model.RentalRequestRole;
import com.example.demo.repository.RentalRequestRepository;
import com.soapclient.api.domain.ClientRequestRentalRequest;
import com.soapclient.api.domain.ClientRequestRentalRequestId;
import com.soapclient.api.domain.ServerRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RentalRequestService {

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public List<CarRentalRequest> rentalRequestsForUserIdAndRole(String forUserId, RentalRequestRole role) {
        return this.rentalRequestRepository.findAllByForUserIdAndRole(forUserId, role);
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

    public void acceptRentalRequest(Long id) {
        CarRentalRequest crc = this.rentalRequestRepository.findOneById(id);
        crc.setRole(RentalRequestRole.valueOf("RESERVED"));
        crc.setCreateDate(new Timestamp(System.currentTimeMillis()));

        ClientRequestRentalRequestId crrri = new ClientRequestRentalRequestId();
        crrri.setId(crc.getSecondId());
        crrri.setType("RESERVED");

        template = new WebServiceTemplate(marshaller);
        template.marshalSendAndReceive("http://localhost:8081/ws", crrri);

        this.rentalRequestRepository.save(crc);
    }

    public void declineRentalRequest(Long id) {
        CarRentalRequest crc = this.rentalRequestRepository.findOneById(id);
        crc.setRole(RentalRequestRole.valueOf("CANCELED"));

        ClientRequestRentalRequestId crrri = new ClientRequestRentalRequestId();
        crrri.setId(crc.getSecondId());
        crrri.setType("CANCELED");

        template = new WebServiceTemplate(marshaller);
        template.marshalSendAndReceive("http://localhost:8081/ws", crrri);

        this.rentalRequestRepository.save(crc);
    }

    @Scheduled(fixedRate=2000000) //Oko 30.3 minuta ce se pozicati ova metoda
    public void setRoleOfRentalRequest(){
        System.out.println("do some taskK 00 1");

        ClientRequestRentalRequestId crrri = new ClientRequestRentalRequestId();

        Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme

        //Svi zahtevi ako nakon 12 od kako su postali rezervisani, ne predju u stanje PAID tj ne budu placeni, odbijaju se
        List<CarRentalRequest> allRentalRequestReserved = this.rentalRequestRepository.findAllByRole(RentalRequestRole.valueOf("RESERVED"));
        for(CarRentalRequest crr : allRentalRequestReserved) {
            Timestamp timeCrr = crr.getCreateDate();
            timeCrr.setTime(timeCrr.getTime() + 12*1000*60*60);

            if(tm.after(timeCrr)) {
                crr.setRole(RentalRequestRole.valueOf("CANCELED"));
                this.rentalRequestRepository.save(crr);

                crrri.setId(crr.getSecondId());
                crrri.setType("CANCELED");

                template = new WebServiceTemplate(marshaller);
                template.marshalSendAndReceive("http://localhost:8081/ws", crrri);
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

                crrri.setId(crr.getSecondId());
                crrri.setType("CANCELED");

                template = new WebServiceTemplate(marshaller);
                template.marshalSendAndReceive("http://localhost:8081/ws", crrri);
            }
        }
    }

    public List<CarRentalRequest> findAllCarInRentalEnd(RentalEndDTO rentalEndDTO) {
        Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme

        List<CarRentalRequest> crd = this.rentalRequestRepository.findAllCarInRentalRequestEnd(rentalEndDTO.getFromUserId().toString(), tm,
                false, "PAID");

        return crd;
    }

}
