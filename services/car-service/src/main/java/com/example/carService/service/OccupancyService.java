package com.example.carService.service;

import com.example.carService.dto.OccupancyDTO;
import com.example.carService.model.Car;
import com.example.carService.model.Occupancy;
import com.example.carService.repository.CarRepository;
import com.example.carService.repository.OccupancyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class OccupancyService {

    @Autowired
    private OccupancyRepository occupancyRepository;

    @Autowired
    private CarRepository carRepository;

    public void saveOccupancy(OccupancyDTO occupancyDTO) {
        Car c = carRepository.findOneById(occupancyDTO.getCarId());
        Occupancy o = Occupancy.builder().startDate(occupancyDTO.getStartDate()).endDate(occupancyDTO.getEndDate()).occupancyCar(c).build();

        log.info("New occupancy is created: " + o);
        this.occupancyRepository.save(o);
    }

    public List<Occupancy> findOccupancyAndRequest(Timestamp start_date, Timestamp end_date, Long occupancy_car_id) {
        return this.occupancyRepository.findOccupancyAndRequest(start_date, end_date, occupancy_car_id);
    }
}
