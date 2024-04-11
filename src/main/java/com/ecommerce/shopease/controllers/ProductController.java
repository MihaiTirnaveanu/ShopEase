package com.ecommerce.shopease.controllers;

import com.ecommerce.shopease.dtos.ProductDto;
import com.ecommerce.shopease.models.Product;
import com.ecommerce.shopease.services.ProductService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200/")
@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') OR hasAuthority('user:read')")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/search")
    @PreAuthorize("hasAuthority('admin:read') OR hasAuthority('user:read')")
    public List<ProductDto> searchProductsByName(@RequestParam String query) {
        return productService.searchProductByName(query);
    }

    @GetMapping(path = "/search/predictive")
    @PreAuthorize("hasAuthority('admin:read') OR hasAuthority('user:read')")
    public List<String> getPredictiveSearchSuggestions(@RequestParam String query) {
        return productService.getPredictiveSearchSuggestions(query);
    }

    @GetMapping(path = "/category/{categoryId}")
    @PreAuthorize("hasAuthority('admin:read') OR hasAuthority('user:read')")
    public List<ProductDto> getProductsByCategoryId(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin:delete')")
    public Map<String, String> deleteProduct(@PathVariable Long id){
        return Collections.singletonMap("response", productService.deleteProduct(id));
    }
}
