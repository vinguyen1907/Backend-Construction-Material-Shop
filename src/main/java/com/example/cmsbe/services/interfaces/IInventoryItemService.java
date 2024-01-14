package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.InventoryItem;

public interface IInventoryItemService {
    PaginationDTO<InventoryItem> getAllInventoryItems(int page, int size);
//    int getCount();
    InventoryItem getInventoryItemById(Integer inventoryItemId);
    PaginationDTO<InventoryItem> getAllInventoryItemsByProductName(String productName, Integer warehouseId, int page, int size);
    InventoryItem createInventoryItem(InventoryItem inventoryItem);
    void deleteInventoryItem(Integer inventoryItemId);
}
