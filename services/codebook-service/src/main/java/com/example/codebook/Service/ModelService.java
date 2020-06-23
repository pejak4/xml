package com.example.codebook.Service;

import com.example.codebook.dto.CodebookDTO;
import com.example.codebook.dto.ModelDTO;
import com.example.codebook.model.Brand;
import com.example.codebook.model.FuelType;
import com.example.codebook.model.Model;
import com.example.codebook.model.Transmission;
import com.example.codebook.repository.BrandRepository;
import com.example.codebook.repository.ModelRepository;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    public List<Model> findAll() {
        List<Model> models = this.modelRepository.findAll();
        for(Model m : models) {
            m.setModel(StringEscapeUtils.escapeHtml4(m.getModel()));
        }
        return this.modelRepository.findAll();
    }

    public void deleteModel(Long id) {this.modelRepository.deleteRequest(id);}

    public Model save(ModelDTO model) {
        Brand brand = brandRepository.findOneById(Long.parseLong(model.getBrandId()));

        Model m = Model.builder().model(model.getModel()).brand(brand).build();

        return this.modelRepository.save(m);
    }


}
