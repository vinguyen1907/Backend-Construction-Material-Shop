package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.OrderItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne()
    private InventoryItem inventoryItem;
//    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
    @Positive(message = "Quantity must be positive.")
    private int quantity;

    public OrderItemDTO toDTO() {
        if (inventoryItem == null) {
            return null;
        }
        return new OrderItemDTO(
                inventoryItem.getId(),
                inventoryItem.getProduct().getId(),
                quantity
        );
    }
}
