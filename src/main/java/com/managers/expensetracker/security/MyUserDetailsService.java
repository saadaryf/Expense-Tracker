package com.managers.expensetracker.security;

import com.managers.expensetracker.model.users.MyUserDetails;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUsername(username);
        foundUser.orElseThrow(()->new UsernameNotFoundException("Not Found" + username));
        User user = foundUser.get();
        return new MyUserDetails(user);
    }
}
