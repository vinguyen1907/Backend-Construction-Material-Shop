package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByCustomerId(Integer customerId);
    Integer countByCustomerId(Integer customerId);
    List<Order> findByOrderByCreatedTimeDesc(Limit limit);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderType = ?1 AND o.status = ?2")
    Double sumTotalByOrderTypeAndStatus(OrderType orderType, OrderStatus orderStatus);
}
