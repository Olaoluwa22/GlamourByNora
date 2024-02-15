package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
