package com.sameerasw.ticketin.server.dto;

import java.util.List;

public class CartResponseDTO {
    private List<CartItemDTO> cartItems;
    private double totalPrice;

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}