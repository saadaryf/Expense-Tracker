package com.managers.expensetracker.service.Impl;

import com.managers.expensetracker.model.Transaction;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.model.requests.TransactionRequest;
import com.managers.expensetracker.repository.TransactionRepository;
import com.managers.expensetracker.service.CategoryService;
import com.managers.expensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CategoryService categoryService;
    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void updateTransaction(TransactionRequest transactionRequest, Long id, String type) {
        Optional<Transaction> foundTransaction = transactionRepository.findById(id);
        TransactionType transactionType = "CASH_IN".equals(type) ? TransactionType.CASH_IN : TransactionType.CASH_OUT;
        if(foundTransaction.isPresent()){
            Transaction transaction = foundTransaction.get();
            transaction.setCategory(categoryService.getCategory(transactionRequest.getCategory(),transactionType));
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setDescription(transactionRequest.getDescription());
            transactionRepository.save(transaction);
        }
    }
}
