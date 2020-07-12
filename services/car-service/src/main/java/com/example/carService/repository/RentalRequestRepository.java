package com.example.carService.repository;

import com.example.carService.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface RentalRequestRepository extends  JpaRepository<CarRentalRequest, Long>  {


    List<CarRentalRequest> findAll();
    List<CarRentalRequest> findAllByUserIdAndRentalRequestCarId(String user_id, Long rental_request_car_id);
    CarRentalRequest findOneById(Long id);
    List<CarRentalRequest> findAllByRole(RentalRequestRole role);
    List<CarRentalRequest> findAllByUserIdAndRole(String user_id, RentalRequestRole role);
    List<CarRentalRequest> findAllByForUserIdAndRoleAndAgent(String for_user_id, RentalRequestRole role, Boolean agent);
    List<CarRentalRequest> findAllByUserId(String userId);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from car_rental_request crr where crr.id=:id")
    void deleteRentalRequest(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select c.agent, c.id, c.start_date, c.end_date, c.user_id, c.for_user_id, c.create_date, " +
            "c.role, c.rental_request_car_id from car_rental_request c " +
            "where c.role=:role and c.for_user_id=:for_user_id and c.rental_request_car_id=:rental_request_car_id " +
            "and not (c.start_date>:end_date or c.end_date<:start_date)")
    List<CarRentalRequest> findRentalRequestWhenPaid(@Param("start_date") Timestamp start_date, @Param("end_date") Timestamp end_date,
                                                     @Param("role") String role,
                                                     @Param("for_user_id") String for_user_id,
                                                     @Param("rental_request_car_id") Long rental_request_car_id);

    @Query(nativeQuery = true, value = "select c.agent, c.id, c.start_date, c.end_date, c.user_id, c.for_user_id, c.create_date, " +
            "c.role, c.rental_request_car_id from car_rental_request c " +
            "where c.user_id=:user_id and c.end_date<:end_date and c.rental_request_car_id=:rental_request_car_id ")
    List<CarRentalRequest> findAllRentalFrom(@Param("user_id") String user_id, @Param("end_date") Timestamp end_date,
                                          @Param("rental_request_car_id") Long rental_request_car_id);

}
