package com.managers.expensetracker.model.users;

import com.managers.expensetracker.model.Balance;
import com.managers.expensetracker.model.Transaction;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;

    @OneToOne(mappedBy = "user")
    private Balance balance;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

}