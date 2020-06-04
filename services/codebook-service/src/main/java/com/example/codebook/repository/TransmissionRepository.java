package com.example.codebook.repository;

import com.example.codebook.model.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TransmissionRepository extends JpaRepository<Transmission, Long> {

    List<Transmission> findAll();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from transmission t where t.id=:id")
    void deleteRequest(@Param("id") Long id);
}
