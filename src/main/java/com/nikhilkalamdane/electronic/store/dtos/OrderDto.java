package com.nikhilkalamdane.electronic.store.dtos;

import com.nikhilkalamdane.electronic.store.entities.OrderItem;
import com.nikhilkalamdane.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {
    private String orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT_PAID";
    private int orderAmount;
    private String billingName;
    private String billingAddress;
    private String billingPhone;
    private Date orderDate =  new Date();
    private Date deliveredDate;
    //private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
