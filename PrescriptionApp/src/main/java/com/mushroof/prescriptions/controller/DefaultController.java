package com.mushroof.prescriptions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {
    @GetMapping("/")
    public String home() {
        return "redirect:/prescriptions/list";
    }
}