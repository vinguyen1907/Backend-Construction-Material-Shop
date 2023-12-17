package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByOriginAndUnitPriceBetween(String origin, double minUnitPrice, double maxUnitPrice, Pageable pageable);
    List<Product> findByUnitPriceBetween(double minUnitPrice, double maxUnitPrice,  Pageable pageable);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByNameContainingAndOriginAndUnitPriceBetween(String keyword, String origin, double minPrice, double maxPrice, Pageable pageable);
    List<Product> findByNameContainingAndUnitPriceBetween(String keyword, double minPrice, double maxPrice, Pageable pageable);
}
