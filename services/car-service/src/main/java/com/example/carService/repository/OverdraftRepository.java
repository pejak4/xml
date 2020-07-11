package com.example.carService.repository;

import com.example.carService.model.Overdraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OverdraftRepository extends  JpaRepository<Overdraft, Long>  {
    List<Overdraft> getAllByUserId(Long user_id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from overdraft crr where crr.id=:id")
    void delete(@Param("id") Long id);
}
