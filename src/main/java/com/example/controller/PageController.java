package com.example.controller;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Primary
public class PageController {
    @RequestMapping("/")
    public String index () {
        return "index";
    }
    @RequestMapping("/login")
    public String login () {
        return "login";
    }

}
