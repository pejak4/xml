package com.example.carService.service;

import com.example.carService.dto.RatingCarRequestDTO;
import com.example.carService.dto.RatingRequestAcceptDeclineDTO;
import com.example.carService.model.Car;
import com.example.carService.model.RatingCarRequest;
import com.example.carService.repository.CarRepository;
import com.example.carService.repository.RatingRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RatingRequestService {

    @Autowired
    private RatingRequestRepository ratingRequestRepository;

    @Autowired
    private CarRepository carRepository;

    public RatingCarRequest addRatingCarRequest(RatingCarRequestDTO ratingCarRequestDTO) {
        RatingCarRequest rcr = RatingCarRequest.builder().carId(ratingCarRequestDTO.getCarId()).fromUserId(ratingCarRequestDTO.getFromUserId())
                .rating(ratingCarRequestDTO.getRating()).build();

        log.info("Request for rating has been created: " + rcr);
        return this.ratingRequestRepository.save(rcr);
    }

    public List<RatingCarRequest> findAll() {
        return this.ratingRequestRepository.findAll();
    }

    public void acceptRatingRequest(RatingRequestAcceptDeclineDTO ratingRequestAcceptDeclineDTO) {
        RatingCarRequest rcr = this.ratingRequestRepository.findOneById(ratingRequestAcceptDeclineDTO.getRatingRequestId());
        Car c = this.carRepository.findOneById(rcr.getCarId());

        int numRating = c.getNumOfRating();
        double rating = c.getRating();

        double newRating = (rating*numRating + rcr.getRating()) / (numRating+1);

        c.setRating(newRating);
        c.setNumOfRating(numRating+1);

        this.carRepository.save(c);
        log.info("Rating: " + rcr + " has been accepted and calculated to car: " + c);
        this.ratingRequestRepository.delete(rcr.getId());

        return;
    }

    public void declineRatingRequest(RatingRequestAcceptDeclineDTO ratingRequestAcceptDeclineDTO) {
        RatingCarRequest rcr = this.ratingRequestRepository.findOneById(ratingRequestAcceptDeclineDTO.getRatingRequestId());

        log.info("Rating: " + rcr + " has been declined");
        this.ratingRequestRepository.delete(rcr.getId());

        return;
    }
}
