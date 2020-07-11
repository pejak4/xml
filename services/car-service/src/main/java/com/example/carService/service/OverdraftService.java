package com.example.carService.service;

import com.example.carService.dto.OverdraftDTO;
import com.example.carService.dto.OverdraftIdDTO;
import com.example.carService.model.Overdraft;
import com.example.carService.repository.OverdraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverdraftService {

    @Autowired
    private OverdraftRepository overdraftRepository;

    public List<Overdraft> getAllByUserId(OverdraftDTO id) {
        return this.overdraftRepository.getAllByUserId(id.getUserId());
    }

    public void acceptOverdraft(OverdraftIdDTO overdraftIdDTO) {
        this.overdraftRepository.delete(overdraftIdDTO.getOverdraftId());
    }
}
