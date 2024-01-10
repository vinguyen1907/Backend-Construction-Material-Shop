package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.SaleOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleOrderRepository extends OrderRepository<SaleOrder> {
    List<Order> findByCustomerId(Integer customerId);
    Integer countByCustomerId(Integer customerId);
}
