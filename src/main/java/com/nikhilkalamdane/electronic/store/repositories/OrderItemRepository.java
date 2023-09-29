package com.nikhilkalamdane.electronic.store.repositories;

import com.nikhilkalamdane.electronic.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
