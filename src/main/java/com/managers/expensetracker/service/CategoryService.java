package com.managers.expensetracker.service;

import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.TransactionType;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CategoryService {
    @Transactional
    Category getCategory(String categoryName, TransactionType transactionType);

    @Transactional
    List<Category> getAllCategories();
}
