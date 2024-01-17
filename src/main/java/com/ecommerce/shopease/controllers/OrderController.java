package com.ecommerce.shopease.controllers;

import com.ecommerce.shopease.models.CreateOrderRequest;
import com.ecommerce.shopease.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200/")
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:create')")
    public Map<String, String> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Long shoppingCartId = createOrderRequest.getShoppingCartId();
        String code = createOrderRequest.getCode();
        orderService.createOrder(shoppingCartId, code);
        return Collections.singletonMap("response", "Order created successfully.");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public Map<String, Object> getAllOrders() {
        return Collections.singletonMap("orders", orderService.getAllOrders());
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public Map<String, String> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Collections.singletonMap("response", "Order " + id + " has been canceled.");
    }
}
