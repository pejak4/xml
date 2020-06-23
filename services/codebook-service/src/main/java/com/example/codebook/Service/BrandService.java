package com.example.codebook.Service;

import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.model.Brand;
import com.example.codebook.model.Model;
import com.example.codebook.repository.BrandRepository;
import com.example.codebook.repository.ModelRepository;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    public List<Brand> findAll() {
        List<Brand> brands = this.brandRepository.findAll();
        for(Brand b : brands) {
            b.setBrand(StringEscapeUtils.escapeHtml4(b.getBrand()));
        }
        return brands;
    }

    public void deleteBrand(Long id) {
        Brand brand = this.brandRepository.findOneById(id);
        for (Model m : brand.getModelList()) {
            this.modelRepository.deleteRequest(m.getId());
        }
        this.brandRepository.deleteRequest(id);
    }

    public Brand save(CodebookDTO brand) {
        Brand b = Brand.builder().brand(brand.getId()).build();


        return this.brandRepository.save(b);
    }
}
