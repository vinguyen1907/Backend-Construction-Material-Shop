package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.dto.InventoryItemListDTO;
import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.InventoryItem;

import java.util.List;

public interface IInventoryItemService {
    PaginationDTO<InventoryItem> getAllInventoryItems(int page, int size);
//    int getCount();
    InventoryItem getInventoryItemById(Integer inventoryItemId);
    PaginationDTO<InventoryItem> getAllInventoryItemsByProductName(String keyword, Integer warehouseId, int page, int size);
    InventoryItem createInventoryItem(InventoryItem inventoryItem);
    void deleteInventoryItem(Integer inventoryItemId);
}