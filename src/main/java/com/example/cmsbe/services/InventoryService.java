package com.example.cmsbe.services;

import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.InventoryItem;
import com.example.cmsbe.repositories.InventoryItemRepository;
import com.example.cmsbe.services.interfaces.IInventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public PaginationDTO<InventoryItem> getAllInventoryItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        long total = inventoryItemRepository.count();
        List<InventoryItem> items = inventoryItemRepository.findAll(pageable).getContent();
         return new PaginationDTO<>(
                 total / size + (total % size == 0 ? 0 : 1),
                 total,
                 page,
                 size,
                 items
         );
    }

    @Override
    public InventoryItem getInventoryItemById(Integer inventoryItemId) {
        return inventoryItemRepository.findById(inventoryItemId).orElseThrow(() -> new EntityNotFoundException("Inventory item not found"));
    }

    @Override
    public PaginationDTO<InventoryItem> getAllInventoryItemsByProductName(String productName, Integer warehouseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var result = inventoryItemRepository.findByProductNameAndWarehouseId(productName, warehouseId, pageable);
        return new PaginationDTO<>(result);
    }

    @Override
    public InventoryItem createInventoryItem(InventoryItem inventoryItem) {
        return inventoryItemRepository.save(inventoryItem);
    }

    @Override
    public void deleteInventoryItem(Integer inventoryItemId) {
        inventoryItemRepository.deleteById(inventoryItemId);
    }
}
