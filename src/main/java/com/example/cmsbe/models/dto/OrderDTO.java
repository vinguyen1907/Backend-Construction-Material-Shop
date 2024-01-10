package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    protected Integer id;
    protected Integer createdUserId;
    protected Double discount;
    protected OrderStatus status;
    protected OrderType orderType;
    protected Double total;
    // Auditing variable
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDateTime updatedTime;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.createdUserId = order.getCreatedUser().getId();
        this.discount = order.getDiscount();
        this.status = order.getStatus();
        this.orderType = order.getOrderType();
        this.total = order.getTotal();
        this.createdTime = order.getCreatedTime();
        this.updatedTime = order.getUpdatedTime();
    }
}
