package com.francelino.estoqueMercadoria.repositories;

import com.francelino.estoqueMercadoria.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
