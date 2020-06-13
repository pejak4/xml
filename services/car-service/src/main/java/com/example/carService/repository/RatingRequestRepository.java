package com.example.carService.repository;

import com.example.carService.model.Car;
import com.example.carService.model.RatingCarRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RatingRequestRepository extends JpaRepository<RatingCarRequest, Long> {
    List<RatingCarRequest> findAll();
    RatingCarRequest findOneById(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from rating_car_request crr where crr.id=:id")
    void delete(@Param("id") Long id);
}
