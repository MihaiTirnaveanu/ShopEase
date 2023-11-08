package com.ecommerce.shopease.dtos;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private String specifications;

    private int stock;

    private String provider;

    private String description;

    private Long categoryId;
}
