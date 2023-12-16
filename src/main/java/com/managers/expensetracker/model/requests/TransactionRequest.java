package com.managers.expensetracker.model.requests;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class TransactionRequest {

    @NotBlank(message = "category can't be empty ")
    private String category;

    @NotNull(message = "Amount field can't be empty!")
    @Positive(message = "Amount should be a positive number!")
    private Double amount;

    @NotNull(message = "You must have to enter date.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Size(max = 150,message = "Description size cant exceed 150 words!")
    private String description;
}
