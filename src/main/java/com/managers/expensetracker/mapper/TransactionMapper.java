package com.managers.expensetracker.mapper;

import com.managers.expensetracker.model.Transaction;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.model.requests.TransactionRequest;
import com.managers.expensetracker.model.responses.TransactionResponse;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TransactionMapper {
    @Autowired
    CategoryService categoryService;

    public Transaction mapToEntity(TransactionRequest transactionRequest, User user, TransactionType transactionType){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        LocalDateTime  dateTime = LocalDateTime.of(transactionRequest.getDate(), LocalTime.now());
        transaction.setDate(dateTime);
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setTransactionType(transactionType);
        transaction.setCategory(categoryService.getCategory(transactionRequest.getCategory(),transactionType));
        transaction.setUser(user);
        return transaction;
    }

    public TransactionResponse mapToDTO(Transaction transaction){
        TransactionResponse transactionResponse =new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setName(transaction.getCategory().getName());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setTransactionType(transaction.getTransactionType());
        return transactionResponse;
    }
}
