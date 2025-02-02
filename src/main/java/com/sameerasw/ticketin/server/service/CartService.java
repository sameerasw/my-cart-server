package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.CartItem;
import com.sameerasw.ticketin.server.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getCartItemsByCustomerId(long customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}