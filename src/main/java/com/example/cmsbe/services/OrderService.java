package com.example.cmsbe.services;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.services.interfaces.IOrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getAllOrders(int page, int size) {
        Sort sort = Sort.by("createdTime").descending();
        Pageable pageable = PageRequest.of(page, size).withSort(sort);
        return orderRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        // Save order first to generate id
        var savedOrder = orderRepository.save(order);
        Integer orderId = savedOrder.getId();
        // Update order id to all order items
        List<OrderItem> newOrderItems = new ArrayList<>();
        for (var i = 0; i < savedOrder.getOrderItems().size(); i++) {
            OrderItem newItem = savedOrder.getOrderItems().get(i);
            newItem.setOrder(savedOrder);
            newOrderItems.add(newItem);
        }
        savedOrder.setOrderItems(newOrderItems);
        // update order with new order items
        var result = orderRepository.save(savedOrder);

        // Flush changes to the database
        entityManager.flush();
        // Clear all entities to get the latest data from the database
        entityManager.clear();
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order updateOrder(Integer orderId, Order order) {
        return null;
    }

    @Override
    public void deleteOrder(Integer orderId) {

    }

    @Override
    public List<Order> searchByIdAndStatusAndCreatedTime(int page, int size, Integer id, OrderStatus status, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<Order> searchByCustomerNameAndStatusAndCreatedTime(int page, int size, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
