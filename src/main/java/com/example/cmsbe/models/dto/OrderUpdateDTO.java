package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderUpdateDTO {
    private Integer createdUserId;
    private Integer customerId;
    private LocalDateTime createdTime;
    private Double depositedMoney;
    private Double discount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;
}
