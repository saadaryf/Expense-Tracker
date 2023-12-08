package com.managers.expensetracker.service.Impl;

import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.repository.CategoryRepository;
import com.managers.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getCategory(String categoryName, TransactionType categoryType) {
        return categoryRepository.findByNameAndCategoryType(categoryName,categoryType);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
