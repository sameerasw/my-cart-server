package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByPrice(double price);
}
