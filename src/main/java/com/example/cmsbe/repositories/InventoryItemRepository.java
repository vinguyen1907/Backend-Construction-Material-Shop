package com.example.cmsbe.repositories;

import com.example.cmsbe.models.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    @Query("SELECT i FROM InventoryItem i " +
            "JOIN i.product p " +
            "JOIN i.warehouse w " +
            "WHERE p.name LIKE %:productName% AND (:warehouseId IS NULL OR w.id = :warehouseId)")
    Page<InventoryItem> findByProductNameAndWarehouseId(@Param("productName") String productName, @Param("warehouseId") Integer warehouseId, Pageable pageable);
}
