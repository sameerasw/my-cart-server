package com.sameerasw.ticketin.server.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private int ticketRetrievalRate;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> purchaseHistory;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    public Customer() {
    }

    public Customer(String name, String email, String password) {
        super(name, email, password);
    }

    public Customer(String name, String email, int ticketRetrievalRate) {
        super(name, email, true);
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public Customer(String name, String email) {
        super(name, email);
    }

    public int getTicketRetrievalRate() {
        return this.ticketRetrievalRate;
    }

    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    public List<CartItem> getPurchaseHistory() {
        return this.purchaseHistory;
    }

    public void updatePurchaseHistory(CartItem cartItem) {
        this.purchaseHistory.add(cartItem);
    }

    public void clearCart() {
        this.cartItems.clear();
    }

//    public List<TicketDTO> getTickets() {
//        return tickets.stream()
//                .map(ticket -> new TicketDTO(
//                        ticket.getEventItem().getName(),
//                        ticket.getTicketId().toString(),
//                        ticket.getEventItem().getImageUrl(),
//                        ticket.getEventItem().getDateTime(),
//                        ticket.getEventItem().getEventId().toString()))
//                .collect(Collectors.toList());
//    }
}