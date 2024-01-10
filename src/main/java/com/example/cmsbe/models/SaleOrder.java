package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PurchaseOrderDTO;
import com.example.cmsbe.models.dto.SaleOrderDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("SALE")
@ToString(exclude = {"orderItems", "debt"})
@NoArgsConstructor
@Data
public class SaleOrder extends Order {
    @ManyToOne
//    @JoinColumn(name = "id")
    private Customer customer;
    private Double depositedMoney;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
    @OneToOne
    @JoinColumn(name = "debt_id", referencedColumnName = "id")
    private Debt debt;

    public SaleOrder(Integer id,
                     User createdUser,
                     Double discount,
                     OrderStatus status,
                     OrderType orderType,
                     Double total,
                     Customer customer,
                     Double depositedMoney,
                     List<OrderItem> orderItems,
                     Debt debt,
                     LocalDateTime createdTime,
                     LocalDateTime updatedTime) {
        super(id, createdUser, discount, status, orderType, total, createdTime, updatedTime);
        this.customer = customer;
        this.depositedMoney = depositedMoney;
        this.orderItems = orderItems;
        this.debt = debt;
    }

    public void setOrderItemsAndCalculateTotal(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.getQuantity() * item.getInventoryItem().getProduct().getUnitPrice();
        }
        this.total = total;
    }

    public OrderDTO toDTO() {
        return new SaleOrderDTO(
                this.id,
                this.createdUser,
                this.discount,
                this.status,
                this.orderType,
                this.total,
                this.createdTime,
                this.updatedTime,
                this.customer,
                this.depositedMoney,
                this.orderItems,
                this.debt
        );
    }
}
