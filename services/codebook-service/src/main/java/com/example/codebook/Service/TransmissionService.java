package com.example.codebook.Service;

import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.model.FuelType;
import com.example.codebook.model.Transmission;
import com.example.codebook.repository.TransmissionRepository;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmissionService {

    @Autowired
    private TransmissionRepository transmissionRepository;

    public List<Transmission> findAll() {
        List<Transmission> transmissions = this.transmissionRepository.findAll();
        for(Transmission t : transmissions) {
            t.setTransmission(StringEscapeUtils.escapeHtml4(t.getTransmission()));
        }
        return this.transmissionRepository.findAll();
    }

    public void deleteTransmission(Long id) {this.transmissionRepository.deleteRequest(id);}

    public Transmission save(CodebookDTO transmission) {
        Transmission tr = Transmission.builder().transmission(transmission.getId()).build();

        return this.transmissionRepository.save(tr);
    }

}
