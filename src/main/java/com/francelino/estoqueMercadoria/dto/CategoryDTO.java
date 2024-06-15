package com.francelino.estoqueMercadoria.dto;

import com.francelino.estoqueMercadoria.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    public CategoryDTO() { }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
