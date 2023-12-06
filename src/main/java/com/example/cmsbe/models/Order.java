package com.example.cmsbe.models;

import com.example.cmsbe.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Customer customer;
    private LocalDateTime createdTime;
    private Double depositedMoney;
    private Double discount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
