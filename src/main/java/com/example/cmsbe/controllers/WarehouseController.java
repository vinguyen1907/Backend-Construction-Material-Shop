package com.example.cmsbe.controllers;

import com.example.cmsbe.models.Warehouse;
import com.example.cmsbe.services.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final IWarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouse() {
        return ResponseEntity.ok(warehouseService.getAllWarehouse());
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer warehouseId) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(warehouseId));
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(warehouseService.createWarehouse(warehouse));
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Integer warehouseId, @RequestBody @Valid Warehouse warehouse) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(warehouseId, warehouse));
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Integer warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.noContent().build();
    }
}
