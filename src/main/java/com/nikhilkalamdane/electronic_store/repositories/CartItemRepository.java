package com.nikhilkalamdane.electronic_store.repositories;

import com.nikhilkalamdane.electronic_store.dtos.CartItemDto;
import com.nikhilkalamdane.electronic_store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
