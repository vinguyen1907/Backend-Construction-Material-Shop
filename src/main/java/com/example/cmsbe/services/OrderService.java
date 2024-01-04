package com.example.cmsbe.services;

import com.example.cmsbe.error_handlers.custom_exceptions.NotEnoughQuantityException;
import com.example.cmsbe.models.Debt;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.repositories.DebtRepository;
import com.example.cmsbe.repositories.InventoryItemRepository;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.services.interfaces.IOrderService;
import com.example.cmsbe.utils.ListUtil;
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
    private final DebtRepository debtRepository;
    private final InventoryItemRepository inventoryItemRepository;
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
    public OrderDTO getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found.")).toDTO();
    }

    @Override
    @Transactional
    public OrderDTO createOrder(Order order) {
        // Save order first to generate id
        order = orderRepository.save(order);
        Integer orderId = order.getId();
        // Update order id to all order items
        updateOrderItems(order);
        // Update debt
        updateDebt(order);
        // Update inventory
        decreaseInventory(order);
        // update order with new order items
        orderRepository.save(order);

        // Flush changes to the database
        entityManager.flush();
        // Clear all entities to get the latest data from the database
        entityManager.clear();

        if (orderRepository.findById(orderId).isEmpty()) return null;
        return new OrderDTO(orderRepository.findById(orderId).get());
    }

    private void decreaseInventory(Order order) {
        var orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            var inventoryItem = inventoryItemRepository.findById(orderItem.getInventoryItem().getId()).orElseThrow();
            if (inventoryItem.getQuantity() < orderItem.getQuantity()) {
                throw new NotEnoughQuantityException("Not enough quantity for product " + orderItem.getInventoryItem().getProduct().getName());
            }
            inventoryItem.setQuantity(inventoryItem.getQuantity() - orderItem.getQuantity());
            inventoryItemRepository.save(inventoryItem);
        }
    }

    private void updateOrderItems(Order order) {
        List<OrderItem> newOrderItems = new ArrayList<>();
        for (var i = 0; i < order.getOrderItems().size(); i++) {
            OrderItem newItem = order.getOrderItems().get(i);
            newItem.setOrder(order);
            var inventoryItemId = newItem.getInventoryItem().getId();
            newItem.setInventoryItem(
                    inventoryItemRepository
                            .findById(inventoryItemId)
                            .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + inventoryItemId + " not found."))
            );
            newOrderItems.add(newItem);
        }
        order.setOrderItemsAndCalculateTotal(newOrderItems);
    }

    private void updateDebt(Order order) {
        Debt debt = order.getDebt();
        if (debt == null) return;
        debt.setOrder(order);
        order.setDebt(debt);
        debtRepository.save(debt);
    }

    @Override
    public Order updateOrder(Integer orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with ID" + orderId + " not found."));
        order.setStatus(newStatus);
        if (newStatus == OrderStatus.CANCELLED) {
            revertInventory(order);
        }

        return orderRepository.save(order);
    }

    private void revertInventory(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            var inventoryItem = orderItem.getInventoryItem();
            inventoryItem.setQuantity(inventoryItem.getQuantity() + orderItem.getQuantity());
            inventoryItemRepository.save(inventoryItem);
        }
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
    public PaginationDTO<OrderDTO> searchWithFilter(int page, int size, Integer id, String customerName, OrderStatus status, LocalDate startDate, LocalDate endDate) {
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
        var result = orderRepository.findAll(spec, pageable);
        var orders = result.getContent();
        var orderDTOs = ListUtil.convertToOrderDTOList(orders);
        return new PaginationDTO<>(
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber(),
                result.getSize(),
                orderDTOs
        );
    }
}
