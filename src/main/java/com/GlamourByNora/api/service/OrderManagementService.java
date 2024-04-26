package com.GlamourByNora.api.service;

import com.GlamourByNora.api.model.Order;

import java.util.List;

public interface OrderManagementService {
    Order getOrderByReference(String reference);
    List<Order> getAllOrders();
    List<Order> getProcessingOrders();
    List<Order> getPaidOrders();
    List<Order> getDeliveredOrders();
    List<Order> getCancelledOrders();
}