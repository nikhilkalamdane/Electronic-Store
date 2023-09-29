package com.nikhilkalamdane.electronic.store.services.impl;

import com.nikhilkalamdane.electronic.store.dtos.AddItemToCartRequest;
import com.nikhilkalamdane.electronic.store.dtos.CartDto;
import com.nikhilkalamdane.electronic.store.entities.Cart;
import com.nikhilkalamdane.electronic.store.entities.CartItem;
import com.nikhilkalamdane.electronic.store.entities.Product;
import com.nikhilkalamdane.electronic.store.entities.User;
import com.nikhilkalamdane.electronic.store.exceptions.BadApiRequestException;
import com.nikhilkalamdane.electronic.store.exceptions.ResourceNotFoundException;
import com.nikhilkalamdane.electronic.store.repositories.CartItemRepository;
import com.nikhilkalamdane.electronic.store.repositories.CartRepository;
import com.nikhilkalamdane.electronic.store.repositories.ProductRepository;
import com.nikhilkalamdane.electronic.store.repositories.UserRepository;
import com.nikhilkalamdane.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {



        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity <= 0){
            throw new BadApiRequestException("Requested quantity is not valid !!!");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id !!!"));
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));


        Cart cart = null;


        try{
            cart = cartRepository.findByUser(user).get();

        }catch(NoSuchElementException ex){

            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        //perform cart operation
        List<CartItem> items = cart.getItems();


        //if cart item already present then update
        AtomicBoolean updated = new AtomicBoolean(false);
        items = items.stream().map(item->{
            if(item.getProduct().getProductId().equals(productId)){
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());


       // cart.setItems(updatedItems);


        //create item
        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }

        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);


        System.out.println(updatedCart.getItems());

        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart5 Item not found !!!"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));
        Cart cart =cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!!"));
        cart.getItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));
        Cart cart =cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!!"));

        return mapper.map(cart, CartDto.class);
    }
}
