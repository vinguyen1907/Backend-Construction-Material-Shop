package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderDTO extends OrderDTO {
    List<InventoryItemDTO> newInventoryItems;

    public PurchaseOrderDTO(Integer id,
                            Integer createdUserId,
                            Double discount,
                            OrderStatus status,
                            OrderType orderType,
                            Double total,
                            LocalDateTime createdTime,
                            LocalDateTime updatedTime,
                            List<InventoryItemDTO> newInventoryItems) {
        super(id, createdUserId, discount, status, orderType, total, createdTime, updatedTime);
        this.newInventoryItems = newInventoryItems;
    }
}
