package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findOrderByStatus(String status);
    Optional<Order> findOrderByReference(String reference);
    Optional<Order> findByUserId(Long userId);
}
