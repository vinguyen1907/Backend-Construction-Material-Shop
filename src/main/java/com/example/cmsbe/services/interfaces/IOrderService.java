package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface  IOrderService {
    List<Order> getAllOrders(int page, int size);
    Order getOrderById(Integer id);
    Order createOrder(Order order);
    Order updateOrder(Integer orderId, OrderStatus newStatus);
    void deleteOrder(Integer orderId);
    // customer id, created time, status
    List<Order> searchByIdAndStatusAndCreatedTime(int page, int size, Integer id, OrderStatus status, LocalDate startDate, LocalDate endDate);
    List<Order> searchByCustomerNameAndStatusAndCreatedTime(int page, int size, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate);
    List<Order> searchWithFilter(int page, int size, Integer id, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate);
}
