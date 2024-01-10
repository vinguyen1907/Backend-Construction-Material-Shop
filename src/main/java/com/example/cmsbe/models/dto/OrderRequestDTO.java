package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.*;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderRequestDTO {
    protected Integer id;
    protected User createdUser;
    protected Double discount;
    protected OrderStatus status;
    protected OrderType orderType = OrderType.SALE;
    protected Double total;
    private Customer customer;
    private Double depositedMoney;
    private List<OrderItem> orderItems;
    private Debt debt;
    private List<InventoryItem> newInventoryItems;
    protected LocalDateTime createdTime;
    protected LocalDateTime updatedTime;

    public PurchaseOrder toPurchaseOrder() {
        return new PurchaseOrder(
                this.id,
                this.createdUser,
                this.discount,
                this.status,
                this.orderType,
                this.total,
                this.createdTime,
                this.updatedTime,
                this.newInventoryItems
        );
    }

    public SaleOrder toSaleOrder() {
        return new SaleOrder(
                this.id,
                this.createdUser,
                this.discount,
                this.status,
                this.orderType,
                this.total,
                this.customer,
                this.depositedMoney,
                this.orderItems,
                this.debt,
                this.createdTime,
                this.updatedTime
        );
    }
}
