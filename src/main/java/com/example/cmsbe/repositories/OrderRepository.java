package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
