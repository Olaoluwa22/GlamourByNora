package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.model.Order;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.service.OrderManagementService;
import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.util.InfoGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InfoGetter infoGetter;
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public List<Order> getProcessingOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.PROCESSING.getMessage());
    }
    @Override
    public List<Order> getPaidOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.PAID.getMessage());
    }
    @Override
    public List<Order> getDeliveredOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.DELIVERED.getMessage());
    }
    @Override
    public List<Order> getCancelledOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.CANCELLED.getMessage());
    }
}