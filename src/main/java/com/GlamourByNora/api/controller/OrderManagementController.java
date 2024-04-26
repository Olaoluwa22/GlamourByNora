package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.model.Order;
import com.GlamourByNora.api.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order_management")
public class OrderManagementController {
    @Autowired
    private OrderManagementService orderManagementService;

    @GetMapping("/order-by-reference")
    public Order getOrderByReference(@RequestBody String reference){
        return orderManagementService.getOrderByReference(reference);
    }
    @GetMapping("/all-orders")
    public List<Order> getAllOrders(){
        return orderManagementService.getAllOrders();
    }
    @GetMapping("/processing-orders")
    public List<Order> getProcessingOrders(){
        return orderManagementService.getProcessingOrders();
    }
    @GetMapping("/paid-orders")
    public List<Order> getPaidOrders(){
        return orderManagementService.getPaidOrders();
    }
    @GetMapping("/delivered-orders")
    public List<Order> getDeliveredOrders(){
        return orderManagementService.getDeliveredOrders();
    }
    @GetMapping("/cancelled-orders")
    public List<Order> getCancelledOrders(){
        return orderManagementService.getCancelledOrders();
    }
}