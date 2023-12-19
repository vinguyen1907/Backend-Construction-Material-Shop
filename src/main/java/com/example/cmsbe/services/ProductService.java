package com.example.cmsbe.services;

import com.example.cmsbe.models.Product;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository repository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(
            Integer productId,
            String name,
            String origin,
            String description,
            Double unitPrice,
            String calculationUnit,
            MultipartFile image
    ) {
        var product = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (name != null) product.setName(name);
        if (origin != null) product.setOrigin(origin);
        if (description != null) product.setDescription(description);
        if (unitPrice != null) product.setUnitPrice(unitPrice);
        if (calculationUnit != null) product.setCalculationUnit(calculationUnit);
        if (image != null) {
            var result = cloudinaryService.uploadFile(image, "cms/products/", product.getName());
            product.setImageUrl((String) result.get("url"));
        }
        return repository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByOriginAndPriceBetween(int page, int size, String origin, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByOriginAndUnitPriceBetween(origin, minPrice, maxPrice, pageable);
    }

    @Override
    public List<Product> getProductsByPriceBetween(int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByUnitPriceBetween(minPrice, maxPrice, pageable);
    }

    @Override
    public List<Product> searchProductByOriginAndPriceBetween(String keyword, int page, int size, String origin, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNameContainingAndOriginAndUnitPriceBetween(keyword, origin, minPrice, maxPrice, pageable);
    }

    @Override
    public List<Product> searchProductByPriceBetween(String keyword, int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNameContainingAndUnitPriceBetween(keyword, minPrice, maxPrice, pageable);
    }
}
