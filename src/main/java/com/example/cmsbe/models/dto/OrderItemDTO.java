package com.example.cmsbe.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Integer productId;
    private Integer quantity;
}
