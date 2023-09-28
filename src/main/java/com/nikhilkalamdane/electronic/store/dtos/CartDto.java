package com.nikhilkalamdane.electronic.store.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikhilkalamdane.electronic.store.entities.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto user;


    private List<CartItemDto> items = new ArrayList<>();
}
