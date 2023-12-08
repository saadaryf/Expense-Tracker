package com.managers.expensetracker.model.requests;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class TransactionRequest {
    private String category;
    private Double amount;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private String description;
}
