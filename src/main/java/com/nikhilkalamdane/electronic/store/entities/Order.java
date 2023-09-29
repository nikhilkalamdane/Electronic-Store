package com.nikhilkalamdane.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    private String orderId;

    //PENDING, DISPATCHED, DELIVERED
    private String orderStatus;

    //NOT-PAID, PAID
    private String paymentStatus;

    private int orderAmount;

    private String billingName;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private Date orderDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


}
