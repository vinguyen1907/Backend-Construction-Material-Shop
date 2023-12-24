package com.example.cmsbe.controllers;

import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.InventoryItem;
import com.example.cmsbe.services.interfaces.IInventoryItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryItemController {
    private final IInventoryItemService inventoryItemService;

    @GetMapping
    public ResponseEntity<PaginationDTO<InventoryItem>> getAllInventoryItems(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(inventoryItemService.getAllInventoryItems(page, size));
    }

    @GetMapping("/{inventoryItemId}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Integer inventoryItemId) throws EntityNotFoundException {
        return ResponseEntity.ok(inventoryItemService.getInventoryItemById(inventoryItemId));
    }

    @GetMapping("/search")
    public ResponseEntity<PaginationDTO<InventoryItem>> getInventoryItemsByProductId(
            @RequestParam String keyword,
            @RequestParam(required = false) Integer warehouseId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        // warehouseId == null => all warehouses
        return ResponseEntity.ok(inventoryItemService.getAllInventoryItemsByProductName(keyword, warehouseId, page, size));
    }

    @PostMapping
    public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem inventoryItem) {
        return ResponseEntity.ok(inventoryItemService.createInventoryItem(inventoryItem));
    }

    @DeleteMapping("/{inventoryItemId}")
    public ResponseEntity<String> deleteInventoryItem(@PathVariable Integer inventoryItemId) {
        inventoryItemService.deleteInventoryItem(inventoryItemId);
        return ResponseEntity.ok("Inventory item deleted successfully");
    }
}
