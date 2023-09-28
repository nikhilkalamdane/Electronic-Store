package com.nikhilkalamdane.electronic.store.repositories;

import com.nikhilkalamdane.electronic.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
