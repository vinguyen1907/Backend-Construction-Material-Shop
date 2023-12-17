package com.example.cmsbe.services;

import com.example.cmsbe.models.Product;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository repository;

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, Product product) {
        if (repository.existsById(product.getId())) {
            product.setId(productId);
            return repository.save(product);
        }
        return null;
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
