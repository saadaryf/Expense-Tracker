package com.managers.expensetracker.service;

import com.managers.expensetracker.model.Transaction;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TransactionService {
    @Transactional
    void saveTransaction(Transaction transaction);

    @Transactional
    List<Transaction> getAllTransactions();

}
