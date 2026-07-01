package com.emprende.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String mostrarMenuPrincipal() {
        return "menuPrincipal";
    }
}
