package com.project.library.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @PostMapping("/login/error")
    public String loginError(){
        return "account/login_error";
    }

    @GetMapping("/register")
    public String register(){
        return "account/register";
    }
}
