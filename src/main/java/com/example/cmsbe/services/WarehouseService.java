package com.example.cmsbe.services;

import com.example.cmsbe.models.Warehouse;
import com.example.cmsbe.repositories.WarehouseRepository;
import com.example.cmsbe.services.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {
    final private WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> getAllWarehouse() {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse getWarehouseById(Integer id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
    }

    @Override
    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse updateWarehouse(Integer id, Warehouse warehouse) {
        Warehouse warehouseToUpdate = warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
        if (warehouse.getAddress() != null) warehouseToUpdate.setAddress(warehouse.getAddress());
        if (warehouse.getCapacity() != null) warehouseToUpdate.setCapacity(warehouse.getCapacity());
        return warehouseRepository.save(warehouseToUpdate);
    }

    @Override
    public void deleteWarehouse(Integer id) {
         warehouseRepository.deleteById(id);
    }
}
