package com.nikhilkalamdane.electronic.store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private int totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
