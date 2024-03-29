package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Order;
import com.example.cmsbe.models.enums.OrderStatus;
import com.example.cmsbe.models.enums.OrderType;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository<T extends Order> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o WHERE o.orderType = 'SALE' ORDER BY o.createdTime LIMIT 15")
    List<Order> findByOrderByCreatedTimeDesc();

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderType = ?1 AND o.status = ?2")
    Double sumTotalByOrderTypeAndStatus(OrderType orderType, OrderStatus orderStatus);

    @Query("select  SUM(total), MONTH(createdTime), YEAR(createdTime) " +
                    "FROM Order " +
                    "WHERE status = 'COMPLETED' AND orderType = 'SALE'" +
                    "GROUP BY YEAR(createdTime), MONTH(createdTime)" +
                    "ORDER BY YEAR(createdTime) asc , MONTH(createdTime)"
            )
    List<Object[]> getMonthlySales();

    @Query(value = "SELECT c.id, c.name, c.phone, c.date_of_birth, c.contact_address, c.tax_code, c.is_deleted, COUNT(*) AS totalOrderAmount, SUM(o.total) as totalOrderValue" +
            "            FROM customer c LEFT JOIN order_table as o ON o.customer_id = c.id" +
            "            WHERE o.status = 'COMPLETED' AND o.order_type = 'SALE'" +
            "            group by c.id, c.name, c.phone, c.date_of_birth, c.contact_address, c.tax_code, c.is_deleted" +
            "            ORDER BY totalOrderValue DESC limit 10", nativeQuery = true)
    List<Object[]> findTop10CustomersByTotalOrderValue();
}
