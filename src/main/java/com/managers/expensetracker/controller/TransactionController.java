package com.managers.expensetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("transaction")
public class TransactionController {

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "cash")
    public String handleTransaction(){
        return "add-transaction";     /*"redirect:/transaction";*/
    }
}
