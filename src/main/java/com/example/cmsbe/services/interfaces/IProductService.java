package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Integer productId, Product product);
    void deleteProduct(Integer id);
    List<Product> getProductsByOriginAndPriceBetween(int page, int size, String origin, double minPrice, double maxPrice);
    List<Product> getProductsByPriceBetween(int page, int size, double minPrice, double maxPrice);
    List<Product> searchProductByOriginAndPriceBetween(String keyword, int page, int size, String origin, double minPrice, double maxPrice);
    List<Product> searchProductByPriceBetween(String keyword, int page, int size, double minPrice, double maxPrice);
}
