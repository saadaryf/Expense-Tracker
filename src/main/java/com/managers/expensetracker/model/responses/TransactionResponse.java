package com.managers.expensetracker.model.responses;

import com.managers.expensetracker.model.TransactionType;
import lombok.Data;

@Data
public class TransactionResponse {
    private String name;
    private String description;
    private Double amount;
    private TransactionType transactionType;
}
