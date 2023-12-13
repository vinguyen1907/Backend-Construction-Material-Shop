package com.example.cmsbe.models;

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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private User createdUser;
    @ManyToOne
//    @JoinColumn(name = "id")
    private Customer customer;
    private LocalDateTime createdTime;
    private Double depositedMoney;
    private Double discount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    List<OrderItem> orderItems;
    @Enumerated(EnumType.STRING)
    OrderType orderType = OrderType.SALE;
}
