package com.managers.expensetracker.controller;

import com.managers.expensetracker.mapper.CategoryMapper;
import com.managers.expensetracker.model.Category;
import com.managers.expensetracker.model.responses.CategoryResponse;
import com.managers.expensetracker.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "/home";
    }

}
