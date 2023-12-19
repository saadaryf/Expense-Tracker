package com.managers.expensetracker.model.responses;

import com.managers.expensetracker.model.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionResponse {
    private Long id;
    private String name;
    private String description;
    private Double amount;
    private TransactionType transactionType;
}
