package com.example.cmsbe.controllers;

import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.Product;
import com.example.cmsbe.services.CloudinaryService;
import com.example.cmsbe.services.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<PaginationDTO<Product>> getAllProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(name = "origin", required = false) String origin,
            @RequestParam(name = "minPrice", defaultValue = "0") double minPrice,
            @RequestParam(name = "maxPrice", defaultValue = "-1") double maxPrice
    ) {
        maxPrice = maxPrice == -1 ? Double.MAX_VALUE : maxPrice;
        if (origin != null) {
            return ResponseEntity.ok(productService.getProductsByOriginAndPriceBetween(page, size, origin, minPrice, maxPrice));
        } else {
            return ResponseEntity.ok(productService.getProductsByPriceBetween(page, size, minPrice, maxPrice));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws EntityNotFoundException {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestParam String name,
            @RequestParam String origin,
            @RequestParam String description,
            @RequestParam double unitPrice,
            @RequestParam String calculationUnit,
            @RequestPart MultipartFile image
    ) {
        var product = Product.builder()
                .name(name)
                .origin(origin)
                .description(description)
                .unitPrice(unitPrice)
                .calculationUnit(calculationUnit);
        if (image != null) {
            var result = cloudinaryService.uploadFile(image, "cms/products/", name);
            product.imageUrl((String) result.get("url"));
        }
        return ResponseEntity.ok(productService.createProduct(product.build()));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double unitPrice,
            @RequestParam(required = false) String calculationUnit,
            @RequestPart(required = false) MultipartFile image
    ) throws EntityNotFoundException {
        var result = productService.updateProduct(
                productId,
                name,
                origin,
                description,
                unitPrice,
                calculationUnit,
                image
        );
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PaginationDTO<Product>> searchProducts(
            @RequestParam(name = "q") String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(name = "origin", required = false) String origin,
            @RequestParam(name = "minPrice", defaultValue = "0") double minPrice,
            @RequestParam(name = "maxPrice", defaultValue = "-1") double maxPrice
    ) {
        maxPrice = maxPrice == -1 ? Double.MAX_VALUE : maxPrice;
        if (origin == null) {
            return ResponseEntity.ok(productService.searchProductByPriceBetween(keyword, page, size, minPrice, maxPrice));
        } else {
            return ResponseEntity.ok(productService.searchProductByOriginAndPriceBetween(keyword, page, size, origin, minPrice, maxPrice));
        }
    }
}
