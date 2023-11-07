package com.ecommerce.shopease.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "specifications")
    private String specifications;

    @Column(name = "stock")
    private int stock;

    @Column(name = "provider")
    private String provider;
}
