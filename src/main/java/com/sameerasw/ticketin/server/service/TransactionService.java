package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.repository.TransactionRepository;
import com.sameerasw.ticketin.server.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Add other service methods for Transaction operations
}