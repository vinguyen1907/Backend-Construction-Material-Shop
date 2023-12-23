package com.example.cmsbe.repositories;

import com.example.cmsbe.models.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    List<InventoryItem> findByProduct_NameContainingIgnoreCaseAndWarehouse_Id(String productName, Integer warehouseId, Pageable pageable);
    long countByProduct_NameContainingIgnoreCaseAndWarehouse_Id(String productName, Integer warehouseId);
}
