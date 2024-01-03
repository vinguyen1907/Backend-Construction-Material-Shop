package com.example.cmsbe.models.dto;

import com.example.cmsbe.models.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemListDTO {
    long pageCount;
    long total;
    Integer currentPage;
    Integer pageSize;
    List<InventoryItem> results;
}
