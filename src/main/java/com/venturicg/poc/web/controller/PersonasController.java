package com.venturicg.poc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonasController {

    @RequestMapping("/personas")
    public String home() {
        return "personas";
    }
}
