package com.example.codebook.repository;

import com.example.codebook.model.Brand;
import com.example.codebook.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findAll();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from model m where m.id=:id")
    void deleteRequest(@Param("id") Long id);
}
