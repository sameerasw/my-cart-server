package com.sameerasw.ticketin.config;

import com.sameerasw.ticketin.handler.TicketWebSocketHandler;
import com.sameerasw.ticketin.handler.TicketCountWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TicketWebSocketHandler ticketWebSocketHandler;
    private final TicketCountWebSocketHandler ticketCountWebSocketHandler;

    public WebSocketConfig(TicketWebSocketHandler ticketWebSocketHandler, TicketCountWebSocketHandler ticketCountWebSocketHandler) {
        this.ticketWebSocketHandler = ticketWebSocketHandler;
        this.ticketCountWebSocketHandler = ticketCountWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ticketWebSocketHandler, "/ws/event/{eventId}/purchases")
                .setAllowedOrigins("*");
        registry.addHandler(ticketCountWebSocketHandler, "/ws/event/{eventId}/tickets")
                .setAllowedOrigins("*");
    }
}