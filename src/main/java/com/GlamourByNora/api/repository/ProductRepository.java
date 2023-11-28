package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
