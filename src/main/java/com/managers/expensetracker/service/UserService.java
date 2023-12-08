package com.managers.expensetracker.service;

import com.managers.expensetracker.model.users.User;
import jakarta.transaction.Transactional;

public interface UserService {
    @Transactional
    boolean saveUser(User user);
}
