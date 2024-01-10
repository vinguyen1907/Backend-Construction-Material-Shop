package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InventoryItemDTO {
    private Integer id;
    private Integer productId;
    private Warehouse warehouse;
    private int quantity;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate importedDate;
    private Double importedPrice;
}
