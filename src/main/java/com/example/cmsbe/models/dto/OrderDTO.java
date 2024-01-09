package com.example.cmsbe.models.dto;

import com.example.cmsbe.config.JacksonLocalDateTimeSerializer;
import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.OrderItem;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id;
    @ManyToOne
    private Integer createdUserId;
    @ManyToOne
    private Integer customerId;
    private Double depositedMoney;
    private Double discount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private Double total;
    private DebtDTO debt;
    // Auditing variable
    @JsonSerialize(using = JacksonLocalDateTimeSerializer.class)
    private LocalDateTime createdTime;
    @JsonSerialize(using = JacksonLocalDateTimeSerializer.class)
    private LocalDateTime updatedTime;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.createdUserId = order.getCreatedUser().getId();
        this.customerId = order.getCustomer() != null ? order.getCustomer().getId() : null;
        this.depositedMoney = order.getDepositedMoney();
        this.discount = order.getDiscount();
        this.status = order.getStatus();
        this.orderItems = order.getOrderItems()
                .stream()
                .map(OrderItem::toDTO).toList();
        this.orderType = order.getOrderType();
        this.total = order.getTotal();
        this.debt = order.getDebt() != null ? order.getDebt().toDTO() : null;
        this.createdTime = order.getCreatedTime();
        this.updatedTime = order.getUpdatedTime();
    }
}
