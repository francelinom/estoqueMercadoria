package com.francelino.estoqueMercadoria.repositories;

import com.francelino.estoqueMercadoria.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
