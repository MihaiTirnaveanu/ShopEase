package com.ecommerce.shopease.services;

import com.ecommerce.shopease.dtos.OrderDto;
import com.ecommerce.shopease.models.Order;
import com.ecommerce.shopease.models.ShoppingCart;
import com.ecommerce.shopease.repos.OrderRepository;
import com.ecommerce.shopease.repos.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ShoppingCartRepository shoppingCartRepository,
                        CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
    }

    @Transactional
    public OrderDto createOrder(Long shoppingCartId, String code) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);

        if (optionalShoppingCart.isPresent()) {
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            Order order = new Order();
            order.setCode(code);
            order.setTotalCost(shoppingCart.getTotalCost());
            order.setSentDate(new Date());
            order.setShoppingCart(shoppingCart);

            orderRepository.save(order);
            cartItemService.deleteAllCartItems();

            return new OrderDto(order);
        } else {
            throw new RuntimeException("Shopping Cart not found with ID: " + shoppingCartId);
        }
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return OrderDto.convertEntityListToDtoList(orders);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Date currentDate = new Date();
            Date sentDate = order.getSentDate();

            if (sentDate != null && currentDate.getTime() <= sentDate.getTime() + 24 * 60 * 60 * 1000) {
                orderRepository.deleteById(orderId);
            } else {
                throw new RuntimeException("Cannot cancel order. A day has passed since the order was created.");
            }
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }

}
