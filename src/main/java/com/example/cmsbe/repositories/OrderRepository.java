package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import org.springframework.data.domain.Limit;
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

    @Query("select  SUM(total), MONTH(createdTime), YEAR(createdTime) from Order" +
                    "WHERE status = 'COMPLETED' AND orderType = 'SALE'" +
                    "GROUP BY YEAR(createdTime), MONTH(createdTime)"
            )
    List<Object[]> getMonthlySales();

    long countByStatus(OrderStatus status);
    @Query(value = "SELECT c.id, c.name, c.phone, c.date_of_birth, c.contact_address, c.tax_code, c.is_deleted, COUNT(*) AS totalOrderAmount, SUM(o.total) as totalOrderValue" +
            "            FROM customer c LEFT JOIN order_table as o ON o.customer_id = c.id" +
            "            WHERE o.status = 'COMPLETED' AND o.order_type = 'SALE'" +
            "            group by c.id, c.name, c.phone, c.date_of_birth, c.contact_address, c.tax_code, c.is_deleted" +
            "            ORDER BY totalOrderValue DESC limit 10", nativeQuery = true)
    List<Object[]> findTop10CustomersByTotalOrderValue();
}
