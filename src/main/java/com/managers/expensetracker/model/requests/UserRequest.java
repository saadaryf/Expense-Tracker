package com.managers.expensetracker.model.requests;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String username;
    private String password;
}
