package com.managers.expensetracker.model;

import com.managers.expensetracker.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CategoryDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {

            List<String> cashInCategories = Arrays.asList("Salary", "Business", "Savings", "Rent", "Loan", "Other");
            for (String categoryName : cashInCategories) {
                Category category = new Category();
                category.setName(categoryName);
                category.setCategoryType(TransactionType.CASH_IN);
                categoryRepository.save(category);
            }
            List<String> cashOutCategories = Arrays.asList(
                    "Groceries", "Fuel", "Food", "Vehicle", "Taxi",
                    "Clothes", "Shopping", "Electricity", "Gas", "Water",
                    "Internet", "Rent", "House", "Gym", "Beauty",
                    "Vacation", "Health", "Education", "Loan", "Pets",
                    "Insurance", "Gifts", "Donations", "Tax", "Other"
            );
            for (String categoryName : cashOutCategories){
                Category category = new Category();
                category.setName(categoryName);
                category.setCategoryType(TransactionType.CASH_OUT);
                categoryRepository.save(category);
            }


        }
    }
}
