package com.example.codebook.Service;

import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.model.FuelType;
import com.example.codebook.model.Transmission;
import com.example.codebook.repository.TransmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmissionService {

    @Autowired
    private TransmissionRepository transmissionRepository;

    public List<Transmission> findAll() {return this.transmissionRepository.findAll();}

    public void deleteTransmission(Long id) {this.transmissionRepository.deleteRequest(id);}

    public Transmission save(CodebookDTO transmission) {
        Transmission tr = Transmission.builder().transmission(transmission.getId()).build();

        return this.transmissionRepository.save(tr);
    }

}
