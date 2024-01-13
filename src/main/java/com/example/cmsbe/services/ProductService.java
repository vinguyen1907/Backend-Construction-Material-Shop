package com.example.cmsbe.services;

import com.example.cmsbe.models.Product;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

//    @Override
//    public PaginationDTO<Product> getAllProducts() {
//        var products = repository.findAll();
//        var total = repository.count();
//
//        return new PaginationDTO<>(
//                total /
//                repository.findAll());
//        return repository.findAll();
//    }


    @Override
    public PaginationDTO<Product> getAllProducts(Pageable pageable) {
        return new PaginationDTO<>(productRepository.findAll(pageable));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
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
        var product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (name != null) product.setName(name);
        if (origin != null) product.setOrigin(origin);
        if (description != null) product.setDescription(description);
        if (unitPrice != null) product.setUnitPrice(unitPrice);
        if (calculationUnit != null) product.setCalculationUnit(calculationUnit);
        if (image != null) {
            var result = cloudinaryService.uploadFile(image, "cms/products/", product.getName());
            product.setImageUrl((String) result.get("url"));
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public PaginationDTO<Product> getProductsByOriginAndPriceBetween(int page, int size, String origin, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var result = productRepository.findByOriginAndUnitPriceBetween(origin, minPrice, maxPrice, pageable);
        return new PaginationDTO<>(result);
    }

    @Override
    public PaginationDTO<Product> getProductsByPriceBetween(int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var result = productRepository.findByUnitPriceBetween(minPrice, maxPrice, pageable);
        return new PaginationDTO<>(result);
    }

    @Override
    public PaginationDTO<Product> search(String keyword, String origin, double minPrice, double maxPrice, String calculationUnit, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Product> spec = Specification.where(null);
        spec =spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
        if (origin != null && !origin.isEmpty()) {
            spec =spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("origin"), "%" + origin + "%"));
        }
        spec =spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), minPrice));
        spec =spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), maxPrice));
        if (calculationUnit != null && !calculationUnit.isEmpty()) {
            spec =spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("calculationUnit"), calculationUnit));
        }
        var result = productRepository.findAll(spec, pageable);
        return new PaginationDTO<>(result);
    }

    @Override
    public PaginationDTO<Product> searchProductByPriceBetween(String keyword, int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var results = productRepository.findByNameContainingAndUnitPriceBetween(keyword, minPrice, maxPrice, pageable);
        return new PaginationDTO<>(results);
    }
}
