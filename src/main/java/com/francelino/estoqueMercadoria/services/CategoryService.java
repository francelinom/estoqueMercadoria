package com.francelino.estoqueMercadoria.services;

import com.francelino.estoqueMercadoria.dto.CategoryDTO;
import com.francelino.estoqueMercadoria.entities.Category;
import com.francelino.estoqueMercadoria.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();

        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> objCategory = categoryRepository.findById(id);
        Category categoryEntity = objCategory.get();
        return new CategoryDTO(categoryEntity);
    }
}
