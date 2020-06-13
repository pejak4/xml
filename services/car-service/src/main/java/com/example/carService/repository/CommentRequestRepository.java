package com.example.carService.repository;

import com.example.carService.model.CommentCarRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRequestRepository extends JpaRepository<CommentCarRequest, Long> {
    List<CommentCarRequest> findAll();
    CommentCarRequest findOneById(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from comment_car_request crr where crr.id=:id")
    void delete(@Param("id") Long id);
}
