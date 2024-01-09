package com.example.cmsbe.controllers;

import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.services.OrderService;
import com.example.cmsbe.services.interfaces.IOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @GetMapping
    public ResponseEntity<PaginationDTO<OrderDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(orderService.searchWithFilter(page, size, id, customerName, status, startDate, endDate));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer orderId) throws EntityNotFoundException {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("newest/{size}")
    public ResponseEntity<List<OrderDTO>> getNewestOrders( @PathVariable Integer size) {
        return ResponseEntity.ok(orderService.getNewestOrders(size));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Integer orderId,
            @RequestParam(name = "status") OrderStatus newStatus
    ) throws EntityNotFoundException {
        return ResponseEntity.ok(orderService.updateOrder(orderId, newStatus));
    }

//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
//        orderService.deleteOrder(orderId);
//        return ResponseEntity.noContent().build();
//    }
}
