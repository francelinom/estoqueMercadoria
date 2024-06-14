package com.francelino.estoqueMercadoria.services;


import com.francelino.estoqueMercadoria.dto.RoleDTO;
import com.francelino.estoqueMercadoria.dto.UserDTO;
import com.francelino.estoqueMercadoria.dto.UserInsertDTO;
import com.francelino.estoqueMercadoria.entities.Role;
import com.francelino.estoqueMercadoria.entities.User;
import com.francelino.estoqueMercadoria.repositories.RoleRepository;
import com.francelino.estoqueMercadoria.repositories.UserRepository;
import com.francelino.estoqueMercadoria.services.exceptions.DataBaseException;
import com.francelino.estoqueMercadoria.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);

        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userDTO) {
        User user = new User();
        copyDtoToEntity(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            User user = userRepository.getOne(id);
            copyDtoToEntity(userDTO, user);
            user = userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found. " + id);
        }
    }

    public void delete(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new ResourceNotFoundException("Id not found. " + id);
            }
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found. " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        user.getRoles().clear();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.getOne(roleDTO.getId());
            user.getRoles().add(role);
        }
    }
}
