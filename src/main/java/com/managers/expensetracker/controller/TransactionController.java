package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.CategoryMapper;
import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.model.responses.CategoryResponse;
import com.managers.expensetracker.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.managers.expensetracker.model.TransactionType.CASH_IN;

@Controller
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMapper categoryMapper;
    @GetMapping
    public String LoadCategories(Model model) {
        final Logger logger = LoggerFactory.getLogger(TransactionController.class);

        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map((category -> categoryMapper.mapToDTO(category)))
                .toList();
        logger.info("\n\nCategories: " + categoryResponses + "\n\n");
        model.addAttribute("categories", categoryResponses);
        return "add-transaction";
    }
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "save")
    public String handleTransaction(){
        return "/home";
    }

}
