package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByOriginAndUnitPriceBetween(String origin, double minUnitPrice, double maxUnitPrice, Pageable pageable);
    long countByOriginAndUnitPriceBetween(String origin, double minUnitPrice, double maxUnitPrice);
    List<Product> findByUnitPriceBetween(double minUnitPrice, double maxUnitPrice,  Pageable pageable);
    long countByUnitPriceBetween(double minUnitPrice, double maxUnitPrice);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByNameContainingAndOriginAndUnitPriceBetween(String keyword, String origin, double minPrice, double maxPrice, Pageable pageable);
    long countByNameContainingAndOriginAndUnitPriceBetween(String keyword, String origin, double minPrice, double maxPricex);
    List<Product> findByNameContainingAndUnitPriceBetween(String keyword, double minPrice, double maxPrice, Pageable pageable);
    long countByNameContainingAndUnitPriceBetween(String keyword, double minPrice, double maxPrice);

    @Query("SELECT SUM(p.quantityRemaining) FROM Product p")
    Double getTotalRemainingCapacity();

    @Query("SELECT SUM(p.quantitySold) FROM Product p")
    Double getTotalSoldCapacity();

    @Query("SELECT p FROM Product p ORDER BY (p.quantityRemaining / (p.quantitySold + p.quantityRemaining)) DESC LIMIT 10")
    List<Product> getOnLowestProductStock();

    @Query(nativeQuery = true, value =
            "WITH MonthlySales AS (" +
                    "    SELECT " +
                    "        MONTH(created_time) AS month, " +
                    "        YEAR(created_time) AS year, " +
                    "        product_id, " +
                    "        sum(quantity) AS total_quantity, " +
                    "        product_name " +
                    "    FROM " +
                    "        order_table " +
                    "            JOIN " +
                    "        (SELECT " +
                    "            inven_product.*, " +
                    "            order_id, " +
                    "            quantity " +
                    "        FROM " +
                    "            order_item " +
                    "                JOIN " +
                    "            (SELECT " +
                    "                p.id AS product_id, " +
                    "                p.name AS product_name, " +
                    "                inven_item.id AS inventory_item_id " +
                    "            FROM " +
                    "                inventory_item inven_item " +
                    "                    JOIN product p ON inven_item.product_id = p.id) AS inven_product " +
                    "                ON order_item.inventory_item_id = inven_product.inventory_item_id) AS inven_product_item " +
                    "            ON order_table.id = inven_product_item.order_id " +
                    "    WHERE " +
                    "        order_type = 'SALE' " +
                    "    GROUP BY " +
                    "        MONTH(created_time), " +
                    "        YEAR(created_time), " +
                    "        product_id, " +
                    "        product_name " +
                    "    ORDER BY " +
                    "        product_id, " +
                    "        YEAR(created_time), " +
                    "        MONTH(created_time)" +
                    ")" +
                    "SELECT " +
                    "    current_month.product_id, " +
                    "    MAX(current_month.product_name) AS product_name, " +
                    "    MAX(current_month.total_quantity) AS current_month_value, " +
                    "    COALESCE(SUM(previous_month.total_quantity), 0) AS previous_month_value " +
                    "FROM " +
                    "    MonthlySales current_month " +
                    "LEFT JOIN " +
                    "    MonthlySales previous_month " +
                    "ON " +
                    "    current_month.product_id = previous_month.product_id " +
                    "        AND (" +
                    "        (current_month.year = previous_month.year AND current_month.month = previous_month.month + 1) " +
                    "            OR " +
                    "        (current_month.year = previous_month.year + 1 AND current_month.month = 1 AND previous_month.month = 12)" +
                    "        )" +
                    "GROUP BY " +
                    "    current_month.product_id " +
                    "ORDER BY " +
                    "    current_month.product_id")
    List<Object[]> getAggregatedMonthlySales();
}
