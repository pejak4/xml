package com.example.demo.repository;

import com.example.demo.model.CarRentalDate;
import com.example.demo.model.CarRentalRequest;
import com.example.demo.model.RentalRequestRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface RentalRequestRepository extends JpaRepository<CarRentalRequest, Long> {


    List<CarRentalRequest> findAll();
    CarRentalRequest findOneByUserIdAndRentalRequestCarId(String user_id, Long rental_request_car_id);
    CarRentalRequest findOneById(Long id);
    List<CarRentalRequest> findAllByRole(RentalRequestRole role);
    List<CarRentalRequest> findAllByUserIdAndRole(String user_id, RentalRequestRole role);
    List<CarRentalRequest> findAllByForUserIdAndRole(String for_user_id, RentalRequestRole role);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from car_rental_request crr where crr.id=:id")
    void deleteRentalRequest(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select c.check_mileage, c.second_id, c.id, c.start_date, c.end_date, c.user_id, c.for_user_id, c.create_date, " +
            "c.role, c.rental_request_car_id from car_rental_request c " +
            "where c.role=:role and c.for_user_id=:for_user_id and c.rental_request_car_id=:rental_request_car_id " +
            "and not (c.start_date>:end_date or c.end_date<:start_date)")
    List<CarRentalRequest> findRentalRequestWhenPaid(@Param("start_date") Timestamp start_date, @Param("end_date") Timestamp end_date,
                                                     @Param("role") String role,
                                                     @Param("for_user_id") String for_user_id,
                                                     @Param("rental_request_car_id") Long rental_request_car_id);

    @Query(nativeQuery = true, value = "select c.check_mileage, c.second_id, c.id, c.start_date, c.end_date, c.user_id, c.for_user_id, c.create_date, " +
            "c.role, c.rental_request_car_id from car_rental_request c " +
            "where c.for_user_id=:for_user_id and c.end_date<:end_date and c.check_mileage=:check_mileage and c.role=:role")
    List<CarRentalRequest> findAllCarInRentalRequestEnd(@Param("for_user_id") String for_user_id, @Param("end_date") Timestamp end_date,
                                              @Param("check_mileage") Boolean check_mileage, @Param("role") String role);
}
