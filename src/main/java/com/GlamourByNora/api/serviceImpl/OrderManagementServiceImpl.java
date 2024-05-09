package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.model.CustomerOrder;
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
    public CustomerOrder getOrderByReference(String reference){
        return infoGetter.getOrderByReference(reference);
    }
    @Override
    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public List<CustomerOrder> getProcessingOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.PROCESSING.getMessage());
    }
    @Override
    public List<CustomerOrder> getPaidOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.PAID.getMessage());
    }
    @Override
    public List<CustomerOrder> getDeliveredOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.DELIVERED.getMessage());
    }
    @Override
    public List<CustomerOrder> getCancelledOrders() {
        return infoGetter.getOrderByStatus(ConstantMessages.CANCELLED.getMessage());
    }
}