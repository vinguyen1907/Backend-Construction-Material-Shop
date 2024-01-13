package com.example.cmsbe.services;

import com.example.cmsbe.error_handlers.custom_exceptions.NotEnoughQuantityException;
import com.example.cmsbe.models.*;
import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import com.example.cmsbe.repositories.DebtRepository;
import com.example.cmsbe.repositories.InventoryItemRepository;
import com.example.cmsbe.repositories.OrderRepository;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IOrderService;
import com.example.cmsbe.utils.ListUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
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
    private final OrderRepository<Order> orderRepository;
    private final DebtRepository debtRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;
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

        if (order instanceof PurchaseOrder) {
            handlePurchaseOrder((PurchaseOrder) order);
        } else {
            handleSaleOrder((SaleOrder) order);
        }

        // update order with new order items
        orderRepository.save(order);

        // Flush changes to the database
        entityManager.flush();
        // Clear all entities to get the latest data from the database
        entityManager.clear();

        var reloadedOrder = orderRepository.findById(orderId);
        return reloadedOrder.map(Order::toDTO).orElse(null);
    }

    private void handlePurchaseOrder(PurchaseOrder order) {
        var inventoryItems = order.getNewInventoryItems();
        for (InventoryItem inventoryItem : inventoryItems) {
            inventoryItemRepository.save(inventoryItem);
            updateQuantitiesInProduct(inventoryItem);
        }
    }

    private void handleSaleOrder(SaleOrder order) {
        fillOrderItems(order);
        createDebtInDB(order);
        decreaseInventoryQuantity(order);
    }

    private void decreaseInventoryQuantity(SaleOrder order) {
        var orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            var inventoryItem = inventoryItemRepository.findById(orderItem.getInventoryItem().getId()).orElseThrow(EntityNotFoundException::new);

            boolean isEnoughQuantity = inventoryItem.getQuantity() >= orderItem.getQuantity();
            if (!isEnoughQuantity) {
                throw new NotEnoughQuantityException("Not enough quantity for product " + orderItem.getInventoryItem().getProduct().getName());
            }
            var newQuantity = inventoryItem.getQuantity() - orderItem.getQuantity();
            inventoryItem.setQuantity(newQuantity);
            inventoryItemRepository.save(inventoryItem);

            updateQuantitiesInProduct(orderItem);
        }
    }

    private void updateQuantitiesInProduct(InventoryItem inventoryItem) {
        var product = productRepository.findById(inventoryItem.getProduct().getId()).orElseThrow(EntityNotFoundException::new);
        var newQuantityRemaining = product.getQuantityRemaining() + inventoryItem.getQuantity();
        product.setQuantityRemaining(newQuantityRemaining);
        productRepository.save(product);
    }

    private void updateQuantitiesInProduct(OrderItem orderItem) {
        var product = orderItem.getInventoryItem().getProduct();
        var newQuantityRemaining = product.getQuantityRemaining() - orderItem.getQuantity();
        var newQuantitySold = product.getQuantitySold() + orderItem.getQuantity();
        product.setQuantityRemaining(newQuantityRemaining);
        product.setQuantitySold(newQuantitySold);
        productRepository.save(product);
    }

    private void fillOrderItems(SaleOrder order) {
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

    private void createDebtInDB(SaleOrder order) {
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
        if (order instanceof SaleOrder && newStatus == OrderStatus.CANCELLED) {
            revertInventory((SaleOrder) order);
        }

        return orderRepository.save(order);
    }

    private void revertInventory(SaleOrder order) {
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
    public PaginationDTO<OrderDTO> searchWithFilter(int page, int size, Integer id, String customerName, OrderStatus status, OrderType orderType, LocalDate startDate, LocalDate endDate) {
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
        if (orderType != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderType"), orderType));
        }
        if (startDate != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("createdTime"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("createdTime"), endDate));
        }
        spec = spec.and((root, query, criteriaBuilder) -> {
                    query.orderBy(criteriaBuilder.desc(root.get("createdTime")));
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
        );

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

    @Override
    public List<OrderDTO> getNewestOrders(int size) {
        return orderRepository.findByOrderByCreatedTimeDesc(Limit.of(size))
                .stream().map(Order::toDTO)
                .toList();
    }
}
