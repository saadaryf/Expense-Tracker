package com.managers.expensetracker.model.responses;

import com.managers.expensetracker.model.TransactionType;
import lombok.Data;

@Data
public class CategoryResponse {
    private String name;
    private TransactionType categoryType;
}
