package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
//@SQLDelete(sql = "UPDATE order_table SET is_deleted = true WHERE id=?")
//@SQLRestriction("is_deleted = false")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private User createdUser;
    @ManyToOne
//    @JoinColumn(name = "id")
    private Customer customer;
    private Double depositedMoney;
    private Double discount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
    @Enumerated(EnumType.STRING)
    private OrderType orderType = OrderType.SALE;
    private Double total;
    @OneToOne
    @JoinColumn(name = "debt_id", referencedColumnName = "id")
    private Debt debt;

    // Auditing variable
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
//    private Boolean isDeleted = false;

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
        return new OrderDTO(this);
    }

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
