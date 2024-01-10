package com.example.cmsbe.models;

import com.example.cmsbe.models.dto.OrderDTO;
import com.example.cmsbe.models.dto.PurchaseOrderDTO;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("PURCHASE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseOrder extends Order {
    @OneToMany()
    private List<InventoryItem> newInventoryItems;

    public PurchaseOrder(Integer id,
                         User createdUser,
                         Double discount,
                         OrderStatus status,
                         OrderType orderType,
                         Double total,
                         LocalDateTime createdTime,
                         LocalDateTime updatedTime,
                         List<InventoryItem> newInventoryItems) {
        super(id, createdUser, discount, status, orderType, total, createdTime, updatedTime);
        this.newInventoryItems = newInventoryItems;
    }

    @Override
    public OrderDTO toDTO() {
        return new PurchaseOrderDTO(
                this.id,
                this.createdUser.getId(),
                this.discount,
                this.status,
                this.orderType,
                this.total,
                this.createdTime,
                this.updatedTime,
                this.newInventoryItems.stream().map(InventoryItem::toDTO).toList()
        );
    }
}
