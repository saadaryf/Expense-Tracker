package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.TransactionMapper;
import com.managers.expensetracker.model.Transaction;
import com.managers.expensetracker.model.TransactionType;
import com.managers.expensetracker.model.responses.TransactionResponse;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.service.TransactionService;
import com.managers.expensetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    UserService userService;

    @GetMapping
    public String showTransactionsList(String startDate, String endDate, Model model) {
        List<Transaction> transactions = getFilteredTransactions(startDate, endDate);
        List<TransactionResponse> transactionResponses = transactions.stream()
                .map(transactionMapper::mapToDTO)
                .toList();
        model.addAttribute("transactions", transactionResponses);
        UserProfile(model);
        Double totalCashIn = totalCashIn(transactions, model);
        Double totalCashOut = totalCashOut(transactions, model);
        totalBalance(totalCashIn, totalCashOut, model);
        numberOfCashInTransactions(transactions, model);
        numberOfCashOutTransactions(transactions, model);
        return "home";
    }

    private List<Transaction> getFilteredTransactions(String startDate, String endDate) {
        List<Transaction> transactions = transactionService.getAllTransactions().stream()
                .filter(transaction -> transaction.getUser().getId().equals(getCurrentUser().getId()))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList();
        if (validateDates(startDate,endDate)) {
            LocalDateTime startDateTime = getDateOfSameDayPreviousMonth();
            LocalDateTime endDateTime = getCurrentDate();
            logger.info("default date time: " + startDateTime + endDateTime);
            return transactions.stream()
                    .filter(transaction -> {
                        LocalDateTime transactionDate = transaction.getDate();
                        return transactionDate.isAfter(startDateTime) && transactionDate.isBefore(endDateTime);
                    })
                    .toList();
        } else {
            LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");
            return transactions.stream()
                    .filter(transaction -> {
                        LocalDateTime transactionDate = transaction.getDate();
                        return transactionDate.isAfter(startDateTime) && transactionDate.isBefore(endDateTime);
                    })
                    .toList();
        }
    }
    private boolean validateDates(String startDate, String endDate){
        if ((startDate == null || endDate == null) || (startDate.isEmpty() || endDate.isEmpty())) {
            return true;
        } else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}", startDate) && Pattern.matches("\\d{4}-\\d{2}-\\d{2}", endDate)) {
            LocalDate startLocalDate = parseLocalDate(startDate);
            LocalDate endLocalDate = parseLocalDate(endDate);
            if (isValidCalendarDate(startLocalDate) && isValidCalendarDate(endLocalDate)) {
                return false;
            }
            return true;
        } else {
            return true;
        }
    }
    private LocalDate parseLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    private boolean isValidCalendarDate(LocalDate date) {
        return date != null;
    }
    public void UserProfile(Model model) {
        User user = getCurrentUser();
        model.addAttribute("name", user.getName());
        model.addAttribute("username", user.getUsername());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }

    public Double totalCashIn(List<Transaction> transactions, Model model) {
        Double totalCashIn = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_IN)
                .mapToDouble(Transaction::getAmount)
                .sum();
        model.addAttribute("totalCashIn", totalCashIn);
        return totalCashIn;
    }

    public Double totalCashOut(List<Transaction> transactions, Model model) {
        Double totalCashOut = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_OUT)
                .mapToDouble(Transaction::getAmount)
                .sum();
        model.addAttribute("totalCashOut", totalCashOut);
        return totalCashOut;
    }

    public void totalBalance(Double totalCashIn, Double totalCashOut, Model model) {
        Double totalBalance = totalCashIn - totalCashOut;
        model.addAttribute("totalBalance", totalBalance);
        balancePercentage(totalCashIn, totalCashOut, model);
    }

    public void balancePercentage(Double totalCashIn, Double totalCashOut, Model model) {
        Double balancePercentage = (totalCashOut / totalCashIn) * 100;
        Integer IntegerPercentage = balancePercentage.intValue();
        model.addAttribute("balancePercentage", IntegerPercentage);
    }

    public void numberOfCashInTransactions(List<Transaction> transactions, Model model) {
        Long numberOfCashInTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_IN)
                .count();
        model.addAttribute("numberOfCashInTransactions", numberOfCashInTransactions);
    }

    public void numberOfCashOutTransactions(List<Transaction> transactions, Model model) {
        Long numberOfCashOutTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() == TransactionType.CASH_OUT)
                .count();
        model.addAttribute("numberOfCashOutTransactions", numberOfCashOutTransactions);
    }

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    public static LocalDateTime getDateOfSameDayPreviousMonth() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime
                .minusMonths(1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .withDayOfMonth(Math.min(currentDateTime.getDayOfMonth(), currentDateTime.toLocalDate().lengthOfMonth()));
    }
}
