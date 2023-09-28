package com.nikhilkalamdane.electronic.store.services;

import com.nikhilkalamdane.electronic.store.dtos.AddItemToCartRequest;
import com.nikhilkalamdane.electronic.store.dtos.CartDto;

public interface CartService {

    //add item to cart
    //case 1 - cart is not available for user - First create a cart then add items
    //case 2 - cart available - add the items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    void clearCart(String userid);

    CartDto getCartByUser(String userId);
}
