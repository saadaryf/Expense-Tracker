package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.UserMapper;
import com.managers.expensetracker.model.requests.UserRequest;
import com.managers.expensetracker.model.users.User;
import com.managers.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "save")
    public ResponseEntity<String> saveUser(@ModelAttribute UserRequest userRequest){
        User user = userMapper.mapToEntity(userRequest);
        if(userService.saveUser(user)){
            return ResponseEntity.ok("User Registered Success");
        }else{
            return ResponseEntity.ok("User Already Exists");
        }

    }

}
