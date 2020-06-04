package com.example.codebook.repository;

import com.example.codebook.model.Brand;
import com.example.codebook.model.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    List<FuelType> findAll();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from fuel_type b where b.id=:id")
    void deleteRequest(@Param("id") Long id);
}
