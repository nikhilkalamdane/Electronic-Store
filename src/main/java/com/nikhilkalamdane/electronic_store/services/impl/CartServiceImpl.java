package com.nikhilkalamdane.electronic_store.services.impl;

import com.nikhilkalamdane.electronic_store.dtos.AddItemToCartRequest;
import com.nikhilkalamdane.electronic_store.dtos.CartDto;
import com.nikhilkalamdane.electronic_store.dtos.CartItemDetailsDto;
import com.nikhilkalamdane.electronic_store.dtos.CartItemDto;
import com.nikhilkalamdane.electronic_store.entities.Cart;
import com.nikhilkalamdane.electronic_store.entities.CartItem;
import com.nikhilkalamdane.electronic_store.entities.Product;
import com.nikhilkalamdane.electronic_store.entities.User;
import com.nikhilkalamdane.electronic_store.exceptions.BadApiRequestException;
import com.nikhilkalamdane.electronic_store.exceptions.ResourceNotFoundException;
import com.nikhilkalamdane.electronic_store.repositories.CartItemRepository;
import com.nikhilkalamdane.electronic_store.repositories.CartRepository;
import com.nikhilkalamdane.electronic_store.repositories.ProductRepository;
import com.nikhilkalamdane.electronic_store.repositories.UserRepository;
import com.nikhilkalamdane.electronic_store.services.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Implementation of the CartService interface.
 */
@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        logger.info("Adding item to cart for user: {}", userId);

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        // Validate quantity
        if (quantity <= 0) {
            throw new BadApiRequestException("Requested quantity is not valid !!!");
        }

        // Retrieve product and user
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with given id !!!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));

        Cart cart;

        try {
            // Try to find an existing cart for the user
            cart = cartRepository.findByUser(user).orElse(null);
        } catch (NoSuchElementException ex) {
            // If not found, create a new cart
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        // Perform cart operation
        List<CartItem> items = cart.getItems();

        // If cart item already present then update
        AtomicBoolean updated = new AtomicBoolean(false);
        items = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                // Item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        // Create item if it doesn't exist
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }

        cart.setUser(user);

        // Save the updated cart
        Cart updatedCart = cartRepository.save(cart);

        logger.debug("Updated Cart Items: {}", updatedCart.getItems());

        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        logger.info("Removing item from cart for user: {}", userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not found !!!"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {
        logger.info("Clearing cart for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!!"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        logger.info("Fetching cart for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!!"));

        return mapper.map(cart, CartDto.class);
    }


}
