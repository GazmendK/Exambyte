package com.example.exambytel9.WebUI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerT1 {

    @GetMapping("/")
    public String index() {
        return "main";
    }
}
