package com.example.codebook.repository;

import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {


    List<Brand> findAll();
    Brand findOneById(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from brand b where b.id=:id")
    void deleteRequest(@Param("id") Long id);
}
