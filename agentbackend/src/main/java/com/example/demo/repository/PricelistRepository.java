package com.example.demo.repository;

import com.example.demo.model.Car;
import com.example.demo.model.Pricelist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricelistRepository extends JpaRepository<Pricelist, Long> {
    Pricelist findOneById(Long id);
    List<Pricelist> findAll();
}
