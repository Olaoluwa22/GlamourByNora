package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
