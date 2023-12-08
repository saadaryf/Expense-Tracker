package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.TransactionMapper;
import com.managers.expensetracker.model.Transaction;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.model.responses.TransactionResponse;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.service.TransactionService;
import com.managers.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    UserService userService;
    @GetMapping
    public String showTransactionsList(Model model){
        List<Transaction> transactions = transactionService.getAllTransactions().stream()
                .filter(transaction -> transaction.getUser().getId().equals(getCurrentUser().getId()))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList();
        List<TransactionResponse> transactionResponses = transactions.stream()
                .map(transaction -> transactionMapper.mapToDTO(transaction))
                .toList();
        model.addAttribute("transactions", transactionResponses);
        Double totalCashIn = totalCashIn(transactions, model);
        Double totalCashOut = totalCashOut(transactions, model);
        totalBalance(totalCashIn,totalCashOut,model);
        numberOfCashInTransactions(transactions,model);
        numberOfCashOutTransactions(transactions,model);
        return "home";
    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }
    public Double totalCashIn(List<Transaction> transactions, Model model){
        Double totalCashIn = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_IN)
                .mapToDouble(Transaction::getAmount)
                .sum();
        model.addAttribute("totalCashIn", totalCashIn);
        return totalCashIn;
    }
    public Double totalCashOut(List<Transaction> transactions, Model model){
        Double totalCashOut = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_OUT)
                .mapToDouble(Transaction::getAmount)
                .sum();
        model.addAttribute("totalCashOut", totalCashOut);
        return totalCashOut;
    }
    public void totalBalance(Double totalCashIn, Double totalCashOut, Model model){
        Double totalBalance =  totalCashIn - totalCashOut;
        model.addAttribute("totalBalance",totalBalance);
        balancePercentage(totalCashIn,totalCashOut,model);
    }
    public void balancePercentage(Double totalCashIn, Double totalCashOut, Model model){
        Double balancePercentage = (totalCashOut / totalCashIn) * 100;
        model.addAttribute("balancePercentage",balancePercentage);
    }
    public void numberOfCashInTransactions(List<Transaction> transactions, Model model){
        Long numberOfCashInTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_IN)
                .count();
        model.addAttribute("numberOfCashInTransactions",numberOfCashInTransactions);
    }
    public void numberOfCashOutTransactions(List<Transaction> transactions, Model model){
        Long numberOfCashOutTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_OUT)
                .count();
        model.addAttribute("numberOfCashOutTransactions",numberOfCashOutTransactions);
    }
}
