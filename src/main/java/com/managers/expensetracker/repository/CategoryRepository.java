package com.managers.expensetracker.repository;

import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        Category findByNameAndCategoryType(String categoryName, TransactionType categoryType);
}

