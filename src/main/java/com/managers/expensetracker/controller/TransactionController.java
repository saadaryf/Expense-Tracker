package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.CategoryMapper;
import com.managers.expensetracker.mapper.TransactionMapper;
import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.Transaction;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.model.requests.TransactionRequest;
import com.managers.expensetracker.model.responses.CategoryResponse;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.service.CategoryService;
import com.managers.expensetracker.service.TransactionService;
import com.managers.expensetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.managers.expensetracker.model.TransactionType.CASH_IN;
import static com.managers.expensetracker.model.TransactionType.CASH_OUT;

@Controller
@RequestMapping("transaction")
public class TransactionController {
    final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    UserService userService;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    TransactionService transactionService;

    @GetMapping
    public String loadCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map((category -> categoryMapper.mapToDTO(category)))
                .toList();
        model.addAttribute("categories", categoryResponses);
        return "add-transaction";
    }
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "save")
    public String saveTransaction(@ModelAttribute TransactionRequest transactionRequest, @RequestParam("type") String type){
        User user = getCurrentUser();
        TransactionType transactionType = "cash-in".equals(type) ? CASH_IN : CASH_OUT;
        Transaction transaction = transactionMapper.mapToEntity(transactionRequest,user, transactionType);
        transactionService.saveTransaction(transaction);
        return "redirect:/";
    }
    @PostMapping("update")
    public String updateTransaction(
            @ModelAttribute TransactionRequest transactionRequest,
            @RequestParam("id") Long id,
            @RequestParam("type") String type
    ){
        transactionService.updateTransaction(transactionRequest,id,type);
        return "redirect:/";
    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }


}
