package com.managers.expensetracker.repository;

import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        Category findByNameAndCategoryType(String categoryName, TransactionType categoryType);
}

