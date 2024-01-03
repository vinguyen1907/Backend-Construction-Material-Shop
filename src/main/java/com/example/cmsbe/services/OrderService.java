package com.example.cmsbe.services;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.services.interfaces.IOrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginationDTO<Order> getAllOrders(int page, int size) {
        Sort sort = Sort.by("createdTime").descending();
        Pageable pageable = PageRequest.of(page, size).withSort(sort);
        return new PaginationDTO<>(orderRepository.findAll(pageable));
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));
    }

    @Override
    @Transactional
    public OrderDTO createOrder(Order order) {
        // Save order first to generate id
        var savedOrder = orderRepository.save(order);
        Integer orderId = savedOrder.getId();
        // Update order id to all order items
        List<OrderItem> newOrderItems = new ArrayList<>();
        for (var i = 0; i < savedOrder.getOrderItems().size(); i++) {
            OrderItem newItem = savedOrder.getOrderItems().get(i);
            newItem.setOrder(savedOrder);
            newItem.setProduct(productService.getProductById(newItem.getProduct().getId()));
            newOrderItems.add(newItem);
        }
        savedOrder.setOrderItems(newOrderItems);
        // update order with new order items
        orderRepository.save(savedOrder);

        // Flush changes to the database
        entityManager.flush();
        // Clear all entities to get the latest data from the database
        entityManager.clear();

        if (orderRepository.findById(orderId).isEmpty() ) return null;
        return new OrderDTO(orderRepository.findById(orderId).get());
    }

    @Override
    public Order updateOrder(Integer orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with ID" + orderId + " not found."));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public PaginationDTO<Order> searchByIdAndStatusAndCreatedTime(int page, int size, Integer id, OrderStatus status, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public PaginationDTO<Order> searchByCustomerNameAndStatusAndCreatedTime(int page, int size, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public PaginationDTO<Order> searchWithFilter(int page, int size, Integer id, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate) {
        Specification<Order> spec = Specification.where(null);
        if (id != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
        }
        if (customerName != null && !customerName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("customer").get("name"), "%" + customerName + "%"));
        }
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
        }
        if (startDate != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("createdTime"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("createdTime"), endDate));
        }
        Pageable pageable = PageRequest.of(page, size);
        return new PaginationDTO<>(orderRepository.findAll(spec, pageable));
    }
}
