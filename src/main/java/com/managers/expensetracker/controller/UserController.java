package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.UserMapper;
import com.managers.expensetracker.model.requests.UserRequest;
import com.managers.expensetracker.model.requests.UserUpdateRequest;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("user")
@Validated
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "save")
    public String saveUser(@Valid @ModelAttribute UserRequest userRequest){
        User user = userMapper.mapToEntity(userRequest);
        userService.saveUser(user);
        return "redirect:/login";
    }
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "update")
    public String updateUser(@Valid @ModelAttribute UserUpdateRequest userUpdateRequest){
        User user = getCurrentUser();
        userService.updateUser(userUpdateRequest,user);
        return "redirect:/";
    }
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "delete")
    public String deleteUser(){
        User user = getCurrentUser();
        userService.deleteUser(user);
        return "redirect:/login";
    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }

}
