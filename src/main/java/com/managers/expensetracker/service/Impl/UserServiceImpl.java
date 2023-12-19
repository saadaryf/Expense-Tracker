package com.managers.expensetracker.service.Impl;

import com.managers.expensetracker.model.requests.UserRequest;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.repository.UserRepository;
import com.managers.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if(foundUser.isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()){
            User user = foundUser.get();
            return user;
        }
        return null;
    }
    @Override
    public void updateUser(UserRequest userRequest, User user) {
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
