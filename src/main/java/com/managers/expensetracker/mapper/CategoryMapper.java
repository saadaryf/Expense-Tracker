package com.managers.expensetracker.mapper;

import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.responses.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse mapToDTO(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(category.getName());
        categoryResponse.setCategoryType(category.getCategoryType());
        return categoryResponse;
    }
}
