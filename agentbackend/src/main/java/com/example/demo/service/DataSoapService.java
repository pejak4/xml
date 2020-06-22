package com.example.demo.service;


import com.example.demo.model.Car;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.model.Comment;
import com.example.demo.model.RentalRequestRole;
import com.example.demo.repository.AdvertisementRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.RentalRequestRepository;
import com.soapclient.api.domain.ClientRequestComment;
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

    @Autowired
    private CommentRepository commentRepository;

    public ServerRespond clientRentalRequest(ClientRequestRentalRequest a) {
        //sa druge strane saljem dva zahteva a pozivam ovu istu metodu. Prvi put je pozivam pri pravljenju novog zahteva.
        //A drugi put kada se zahtev plati pa se promeni na stanje Paid, onda saljem ovde i ovde menjam role na Paid.
        //Zbog toga imam dva if-a, u zavisnoti sta radimo.
        CarRentalRequest rentalRequest = this.rentalRequestRepository.findOneBySecondId(a.getId());
        ServerRespond serverRespond = new ServerRespond();
        serverRespond.setId(0);
        if(rentalRequest == null) {
            Instant instant1 = Instant.parse(a.getStartDate());
            Instant instant2 = Instant.parse(a.getEndDate());

            Date startDate = Date.from(instant1);
            Date endDate = Date.from(instant2);

            Timestamp tss = new Timestamp(startDate.getTime());
            Timestamp tse = new Timestamp(endDate.getTime());

            Car c = advertisementRepository.findOneById(Long.parseLong(a.getCarId()));

            Timestamp tm = new Timestamp(System.currentTimeMillis()); //Trenutno vreme

            CarRentalRequest crr = CarRentalRequest.builder().role(RentalRequestRole.PENDING).
                    startDate(tss).endDate(tse).createDate(tm).forUserId(c.getUserId()).check_mileage(false).
                    userId(a.getUserId()).rentalRequestCar(c).secondId(a.getId()).build();

            this.rentalRequestRepository.save(crr);
            serverRespond.setId(crr.getId());
        } else {
            rentalRequest.setRole(RentalRequestRole.PAID);
            this.rentalRequestRepository.save(rentalRequest);
        }

        return serverRespond;
    }

    public void addComment(ClientRequestComment requestComment) {
        Car c = this.advertisementRepository.findOneBySecondId(requestComment.getCarId());

        if(c != null) {
            Comment com = Comment.builder().carId(c.getId()).descriptionComment(requestComment.getDescriptionComment())
            .fromUserId(requestComment.getFromUserId()).build();
//Nisam generisao secondId, jer ga nisam ni poslao ali mislim da to nece biti problem
            this.commentRepository.save(com);
        }
    }

}