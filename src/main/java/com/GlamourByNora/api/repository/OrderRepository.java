package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<List<CustomerOrder>> findOrderByStatus(String status);
    Optional<CustomerOrder> findOrderByReference(String reference);
    Optional<CustomerOrder> findByUserId(Long userId);
    Optional<CustomerOrder> findByUserIdAndStatus(Long userId, String status);
}
