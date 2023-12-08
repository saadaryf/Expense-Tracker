package com.managers.expensetracker.mapper;

import com.managers.expensetracker.model.requests.UserRequest;
import com.managers.expensetracker.model.users.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToEntity(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return user;
    }
}
