package com.example.cmsbe.services;

import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.services.interfaces.IOrderService;
import jakarta.persistence.*;
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
//    private final UserRepository userRepository;
//    private final CustomerRepository customerRepository;
//    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginationDTO<Order> getAllOrders(int page, int size) {
        Sort sort = Sort.by("createdTime").descending();
        Pageable pageable = PageRequest.of(page, size).withSort(sort);
        var items = orderRepository.findAll(pageable).getContent();
        var total = orderRepository.count();
        return new PaginationDTO<>(
                (long) Math.ceil((double) total / size),
                total,
                page,
                size,
                items
        );
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));
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
    public Order updateOrder(Integer orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with ID" + orderId + " not found."));
        order.setStatus(newStatus);
        return orderRepository.save(order);

//        Order order = optionalOrder.get();
//        if (orderUpdateDTO.getCreatedTime() != null) {
//            order.setCreatedTime(orderUpdateDTO.getCreatedTime());
//        }
//        if (orderUpdateDTO.getDepositedMoney() != null) {
//            order.setDepositedMoney(orderUpdateDTO.getDepositedMoney());
//        }
//        if (orderUpdateDTO.getDiscount() != null) {
//            order.setDiscount(orderUpdateDTO.getDiscount());
//        }
//        if (orderUpdateDTO.getStatus() != null) {
//            order.setStatus(orderUpdateDTO.getStatus());
//        }
//        if (orderUpdateDTO.getCreatedUserId() != null) {
//            Optional<User> optionalCreatedUser = userRepository.findById(orderUpdateDTO.getCreatedUserId());
//            optionalCreatedUser.ifPresent(order::setCreatedUser);
//        }
//        if (orderUpdateDTO.getCustomerId() != null) {
//            Optional<Customer> optionalCustomer = customerRepository.findById(orderUpdateDTO.getCustomerId());
//            optionalCustomer.ifPresent(order::setCustomer);
//        }
//        if (orderUpdateDTO.getOrderItems() != null) {
//            List<OrderItem> updatedOrderItems = new ArrayList<>();
//            for (var orderItemDTO : orderUpdateDTO.getOrderItems()) {
//                productRepository.findById(order)
//            }
//        }
//        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public PaginationDTO<Order> searchByIdAndStatusAndCreatedTime(
            int page,
            int size,
            Integer id,
            OrderStatus status,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return null;
    }

    @Override
    public PaginationDTO<Order> searchByCustomerNameAndStatusAndCreatedTime(
            int page,
            int size,
            String customerName,
            OrderStatus status,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return null;
    }

    @Override
    public PaginationDTO<Order> searchWithFilter(
            int page,
            int size,
            Integer id,
            String customerName,
            OrderStatus status,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Specification<Order> spec = Specification.where(null);
        if (id != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), id));
        }
        if (customerName != null && !customerName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("customer").get("name"), "%" + customerName + "%"));
        }
        if (status != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status));
        }
        if (startDate != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("createdTime"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("createdTime"), endDate));
        }
        Pageable pageable = PageRequest.of(page, size);
        var total = orderRepository.count(spec);
        var items = orderRepository.findAll(spec, pageable).getContent();
        return new PaginationDTO<>(
                (long) Math.ceil((double) total / size),
                total,
                page,
                size,
                items
        );
    }
}
