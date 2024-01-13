package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;

import java.time.LocalDate;
import java.util.List;

public interface  IOrderService {
    PaginationDTO<Order> getAllOrders(int page, int size);
    OrderDTO getOrderById(Integer id);
    OrderDTO createOrder(Order order);
    Order updateOrder(Integer orderId, OrderStatus newStatus);
    void deleteOrder(Integer orderId);
    // customer id, created time, status
    PaginationDTO<OrderDTO> searchWithFilter(int page, int size, Integer id, String customerName, OrderStatus status, OrderType orderType, LocalDate startDate, LocalDate endDate);
    List<OrderDTO> getNewestOrders();
}
