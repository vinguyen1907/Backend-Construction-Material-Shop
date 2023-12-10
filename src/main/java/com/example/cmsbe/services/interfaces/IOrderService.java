package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface  IOrderService {
    List<Order> getAllOrders(int page, int size);
    Optional<Order> getOrderById(Integer id);
    Order createOrder(Order order);
    Order updateOrder(Integer orderId, Order order);
    void deleteOrder(Integer orderId);
    // customer id, created time, status
    List<Order> searchByIdAndStatusAndCreatedTime(int page, int size, Integer id, OrderStatus status, LocalDate startDate, LocalDate endDate);
    List<Order> searchByCustomerNameAndStatusAndCreatedTime(int page, int size, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate);
}
