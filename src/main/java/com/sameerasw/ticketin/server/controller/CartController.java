package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.CartItemDTO;
import com.sameerasw.ticketin.server.model.CartItem;
import com.sameerasw.ticketin.server.service.CartService;
import com.sameerasw.ticketin.server.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private MappingService mappingService;

    @GetMapping("/{customerId}") // Get all cart items by customer ID
    public ResponseEntity<List<CartItemDTO>> getCartItemsByCustomerId(@PathVariable long customerId) {
        List<CartItem> cartItems = cartService.getCartItemsByCustomerId(customerId);
        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(mappingService::mapToCartItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cartItemDTOs, HttpStatus.OK);
    }

    @PostMapping // Add a new cart item
    public ResponseEntity<CartItemDTO> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        CartItem cartItem = mappingService.mapToCartItem(cartItemDTO);
        CartItem savedCartItem = cartService.addCartItem(cartItem);
        return new ResponseEntity<>(mappingService.mapToCartItemDTO(savedCartItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartItemId}") // Remove a cart item
    public ResponseEntity<Void> removeCartItem(@PathVariable long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/clear/{customerId}") // Clear all cart items by customer ID
    public ResponseEntity<Void> clearCartItemsByCustomerId(@PathVariable long customerId) {
        cartService.clearCartItemsByCustomerId(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}