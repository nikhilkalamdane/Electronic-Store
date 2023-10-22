package com.nikhilkalamdane.electronic_store.repositories;

import com.nikhilkalamdane.electronic_store.dtos.CartItemDetailsDto;
import com.nikhilkalamdane.electronic_store.entities.Cart;
import com.nikhilkalamdane.electronic_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);
}
