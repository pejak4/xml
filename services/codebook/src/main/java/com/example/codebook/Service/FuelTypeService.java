package com.example.codebook.Service;

import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.model.Brand;
import com.example.codebook.model.FuelType;
import com.example.codebook.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTypeService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    public List<FuelType> findAll(){return this.fuelTypeRepository.findAll();}

    public void deleteFuelType(Long id) {this.fuelTypeRepository.deleteRequest(id);}

    public FuelType save(CodebookDTO fuelType) {
        FuelType ft = FuelType.builder().fuelType(fuelType.getId()).build();

        return this.fuelTypeRepository.save(ft);
    }

}
