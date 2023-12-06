package com.managers.expensetracker.model;

import com.managers.expensetracker.model.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer amount;
    private Date date;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
