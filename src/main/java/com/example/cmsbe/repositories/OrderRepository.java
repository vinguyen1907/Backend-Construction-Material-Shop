package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByCustomerId(Integer customerId);
    Integer countByCustomerId(Integer customerId);
}
