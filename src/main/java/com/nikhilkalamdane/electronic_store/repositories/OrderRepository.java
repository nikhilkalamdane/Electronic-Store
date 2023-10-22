package com.nikhilkalamdane.electronic_store.repositories;

import com.nikhilkalamdane.electronic_store.entities.Order;
import com.nikhilkalamdane.electronic_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);

}
