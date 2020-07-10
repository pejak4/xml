package com.example.demo.service;

import com.example.demo.dto.PricelistDTO;
import com.example.demo.model.Car;
import com.example.demo.model.Pricelist;
import com.example.demo.repository.PricelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PricelistService {

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private  AdvertisementService advertisementService;

    public List<Pricelist> getAll() {
        return this.pricelistRepository.findAll();
    }

    public Pricelist getPricelistById(Long id) {
        return this.pricelistRepository.findOneById(id);
    }

    public void savePricelist(PricelistDTO pricelistDTO) {
        Car car = this.advertisementService.findOneCar(pricelistDTO.getCarId());

        Set<Car> cars = new HashSet<>();
        cars.add(car);

        Pricelist pricelist = Pricelist.builder().monday(pricelistDTO.getMonday()).tuesday(pricelistDTO.getTuesday()).
                wednesday(pricelistDTO.getWednesday()).thursday(pricelistDTO.getThursday()).friday(pricelistDTO.getFriday())
                .saturday(pricelistDTO.getSaturday()).sunday(pricelistDTO.getSunday()).cdw(pricelistDTO.getCdw())
                .kilometer(pricelistDTO.getKilometer()).pricelistCars(cars).build();

        this.pricelistRepository.save(pricelist);
    }
}
