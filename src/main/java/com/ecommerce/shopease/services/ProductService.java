package com.ecommerce.shopease.services;

import com.ecommerce.shopease.dtos.ProductDto;
import com.ecommerce.shopease.models.Category;
import com.ecommerce.shopease.models.Product;
import com.ecommerce.shopease.repos.CategoryRepository;
import com.ecommerce.shopease.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::new).collect(Collectors.toList());
    }

    public ProductDto saveProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Error while retrieving productDto category."));

        Product product = new Product();

        product.setName(productDto.getName());
        product.setSpecifications(productDto.getSpecifications());
        product.setStock(productDto.getStock());
        product.setProvider(productDto.getProvider());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setCost(productDto.getCost());

        return new ProductDto(productRepository.save(product));
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new RuntimeException("Error while retrieving product id"));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Error while retrieving productDto category."));

        product.setName(productDto.getName());
        product.setSpecifications(productDto.getSpecifications());
        product.setStock(productDto.getStock());
        product.setProvider(productDto.getProvider());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setCost(productDto.getCost());

        return new ProductDto(productRepository.save(product));
    }

    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "Product " + id + " has been deleted.";
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(ProductDto::new).collect(Collectors.toList());
    }
}
