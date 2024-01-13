package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.*;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SaleOrderDTO extends OrderDTO {
    private Integer customerId;
    private Double depositedMoney;
    private List<OrderItemDTO> orderItems;
    private DebtDTO debt;

    public SaleOrderDTO(
            Integer id,
            User createdUser,
            Double discount,
            OrderStatus status,
            OrderType orderType,
            Double total,
            LocalDateTime createdTime,
            LocalDateTime updatedTime,
            Customer customer,
            Double depositedMoney,
            List<OrderItem> orderItems,
            Debt debt
    ) {
        super(id, createdUser.getId(), discount, status, orderType, total, createdTime, updatedTime);
        if (customer != null) {
            this.customerId = customer.getId();
        }
        this.depositedMoney = depositedMoney;
        this.orderItems = orderItems.stream().map(OrderItem::toDTO).toList();
        if (debt != null) {
            this.debt = debt.toDTO();
        }
    }
}
