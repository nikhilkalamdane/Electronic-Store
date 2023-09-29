package com.nikhilkalamdane.electronic.store.services;

import com.nikhilkalamdane.electronic.store.dtos.CreateOrderRequest;
import com.nikhilkalamdane.electronic.store.dtos.OrderDto;
import com.nikhilkalamdane.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);


}
