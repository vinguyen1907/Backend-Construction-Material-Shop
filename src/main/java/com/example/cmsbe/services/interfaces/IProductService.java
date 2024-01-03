package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();

    Product getProductById(Integer id);

    Product createProduct(Product product);

    Product updateProduct(
            Integer productId,
            String name,
            String origin,
            String description,
            Double unitPrice,
            String calculationUnit,
            MultipartFile image
    );

    void deleteProduct(Integer id);

    PaginationDTO<Product> getProductsByOriginAndPriceBetween(int page, int size, String origin, double minPrice, double maxPrice);

    PaginationDTO<Product> getProductsByPriceBetween(int page, int size, double minPrice, double maxPrice);

    PaginationDTO<Product> searchProductByOriginAndPriceBetween(String keyword, int page, int size, String origin, double minPrice, double maxPrice);

    PaginationDTO<Product> searchProductByPriceBetween(String keyword, int page, int size, double minPrice, double maxPrice);
}
