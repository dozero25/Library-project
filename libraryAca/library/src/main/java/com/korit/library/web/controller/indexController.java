package com.korit.library.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping({"", "/index"}) // 두가지 입력 가능, localhost8000, localhost8000/index
    public String index(){
        return "index";
    }
}
