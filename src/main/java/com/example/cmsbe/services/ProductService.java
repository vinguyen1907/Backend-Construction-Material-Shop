package com.example.cmsbe.services;

import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.Product;
import com.example.cmsbe.repositories.ProductRepository;
import com.example.cmsbe.services.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository repository;
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
    public PaginationDTO<Product> getProductsByOriginAndPriceBetween(int page, int size, String origin, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var paginationDTO = PaginationDTO.<Product>builder();
        var results = repository.findByOriginAndUnitPriceBetween(origin, minPrice, maxPrice, pageable);
        var total = repository.countByOriginAndUnitPriceBetween(origin, minPrice, maxPrice);
        paginationDTO.total(total);
        paginationDTO.pageCount((long) Math.ceil((double) total / size));
        paginationDTO.currentPage(page);
        paginationDTO.pageSize(size);
        paginationDTO.results(results);
        return paginationDTO.build();
    }

    @Override
    public PaginationDTO<Product> getProductsByPriceBetween(int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var paginationDTO = PaginationDTO.<Product>builder();
        var results = repository.findByUnitPriceBetween(minPrice, maxPrice, pageable);
        var total = repository.countByUnitPriceBetween(minPrice, maxPrice);
        paginationDTO.total(total);
        paginationDTO.pageCount((long) Math.ceil((double) total / size));
        paginationDTO.currentPage(page);
        paginationDTO.pageSize(size);
        paginationDTO.results(results);
        return paginationDTO.build();
    }

    @Override
    public PaginationDTO<Product> searchProductByOriginAndPriceBetween(String keyword, int page, int size, String origin, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var paginationDTO = PaginationDTO.<Product>builder();
        var results = repository.findByNameContainingAndOriginAndUnitPriceBetween(keyword, origin, minPrice, maxPrice, pageable);
        var total = repository.countByNameContainingAndOriginAndUnitPriceBetween(keyword, origin, minPrice, maxPrice);
        paginationDTO.total(total);
        paginationDTO.pageCount((long) Math.ceil((double) total / size));
        paginationDTO.currentPage(page);
        paginationDTO.pageSize(size);
        paginationDTO.results(results);
        return paginationDTO.build();
    }

    @Override
    public PaginationDTO<Product> searchProductByPriceBetween(String keyword, int page, int size, double minPrice, double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        var paginationDTO = PaginationDTO.<Product>builder();
        var results = repository.findByNameContainingAndUnitPriceBetween(keyword, minPrice, maxPrice, pageable);
        var total = repository.countByNameContainingAndUnitPriceBetween(keyword, minPrice, maxPrice);
        paginationDTO.total(total);
        paginationDTO.pageCount((long) Math.ceil((double) total / size));
        paginationDTO.currentPage(page);
        paginationDTO.pageSize(size);
        paginationDTO.results(results);
        return paginationDTO.build();
    }
}
