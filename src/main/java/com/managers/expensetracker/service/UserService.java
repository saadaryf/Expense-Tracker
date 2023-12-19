package com.managers.expensetracker.service;

import com.managers.expensetracker.model.requests.UserRequest;
import com.managers.expensetracker.model.users.User;
import jakarta.transaction.Transactional;

public interface UserService {
    @Transactional
    void saveUser(User user);

    @Transactional
    User findByUsername(String username);

    @Transactional
    void updateUser(UserRequest userRequest, User user);

    @Transactional
    void deleteUser(User user);
}
