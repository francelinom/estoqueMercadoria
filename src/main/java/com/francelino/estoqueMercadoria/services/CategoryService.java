package com.francelino.estoqueMercadoria.services;

import com.francelino.estoqueMercadoria.entities.Category;
import com.francelino.estoqueMercadoria.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
