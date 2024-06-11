package com.francelino.estoqueMercadoria.services;

import com.francelino.estoqueMercadoria.dto.CategoryDTO;
import com.francelino.estoqueMercadoria.dto.ProductDTO;
import com.francelino.estoqueMercadoria.entities.Category;
import com.francelino.estoqueMercadoria.entities.Product;
import com.francelino.estoqueMercadoria.repositories.CategoryRepository;
import com.francelino.estoqueMercadoria.repositories.ProductRepository;
import com.francelino.estoqueMercadoria.services.exceptions.DataBaseException;
import com.francelino.estoqueMercadoria.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = productRepository.findAll(pageRequest);

        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> objproduct = productRepository.findById(id);
        Product productEntity = objproduct
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new ProductDTO(productEntity, productEntity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product product = new Product();
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            Product product = productRepository.getOne(id);
            copyDtoToEntity(productDTO, product);
            product = productRepository.save(product);
            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found. " + id);
        }
    }

    public void delete(Long id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ResourceNotFoundException("Id not found. " + id);
            }
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found. " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setDate(productDTO.getDate());
        product.setImgUrl(productDTO.getImgUrl());
        product.setPrice(productDTO.getPrice());

        product.getCategories().clear();
        for (CategoryDTO cat : productDTO.getCategories()) {
            Category category = categoryRepository.getOne(cat.getId());
            product.getCategories().add(category);
        }
    }
}
