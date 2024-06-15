package com.francelino.estoqueMercadoria.dto;

import com.francelino.estoqueMercadoria.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres")
    @NotBlank(message = "Campo primeiro nome é obrigatório")
    private String firstName;

    @Size(min = 3, max = 50, message = "O sobrenome deve ter entre 3 e 50 caracteres")
    @NotBlank(message = "Campo sobrenome é obrigatório")
    private String lastName;

    @Email(message = "Por favor, informe um email válido")
    private String email;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {
    }
    public UserDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();

        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }
}
