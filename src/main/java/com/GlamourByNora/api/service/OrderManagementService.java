package com.GlamourByNora.api.service;

import com.GlamourByNora.api.model.CustomerOrder;

import java.util.List;

public interface OrderManagementService {
    CustomerOrder getOrderByReference(String reference);
    List<CustomerOrder> getAllOrders();
    List<CustomerOrder> getProcessingOrders();
    List<CustomerOrder> getPaidOrders();
    List<CustomerOrder> getDeliveredOrders();
    List<CustomerOrder> getCancelledOrders();
}