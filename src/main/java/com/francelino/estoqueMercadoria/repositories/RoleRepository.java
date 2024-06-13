package com.francelino.estoqueMercadoria.repositories;

import com.francelino.estoqueMercadoria.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
