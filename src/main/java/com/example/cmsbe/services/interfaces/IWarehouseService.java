package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.Warehouse;

import java.util.List;

public interface IWarehouseService {
    List<Warehouse> getAllWarehouse();
    Warehouse getWarehouseById(Integer id);
    Warehouse createWarehouse(Warehouse warehouse);
    Warehouse updateWarehouse(Integer id, Warehouse warehouse);
    void deleteWarehouse(Integer id);
}
