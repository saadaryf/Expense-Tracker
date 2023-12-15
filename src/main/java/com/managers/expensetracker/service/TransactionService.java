package com.managers.expensetracker.service;

import com.managers.expensetracker.model.Transaction;
import com.managers.expensetracker.model.requests.TransactionRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TransactionService {
    @Transactional
    void saveTransaction(Transaction transaction);

    @Transactional
    List<Transaction> getAllTransactions();

    @Transactional
    void updateTransaction(TransactionRequest transactionRequest, Long id, String type);

    @Transactional
    void deleteTransaction(Long id);

}
