package com.nikhilkalamdane.electronic.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "Cart id is required!")
    private String cartId;

    @NotBlank(message = "User id is required!")
    private String userId;

    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT_PAID";
    private int orderAmount;

    @NotBlank(message = "Name is required!")
    private String billingName;

    @NotBlank(message = "Address is required!")
    private String billingAddress;

    @NotBlank(message = "Phone is required!")
    private String billingPhone;

}
