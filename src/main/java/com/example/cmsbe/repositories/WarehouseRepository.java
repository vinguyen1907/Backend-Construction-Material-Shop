package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    @Query("SELECT SUM(w.capacity) FROM Warehouse w")
    double getCapacity();
}
