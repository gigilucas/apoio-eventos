package com.eventos.apoio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/dashboard"})
    public String index() {
        return "index";
    }

    @GetMapping("/eventos")
    public String eventos() {
        return "eventos";
    }

    @GetMapping("/participantes")
    public String participantes() {
        return "participantes";
    }

    @GetMapping("/inscricoes")
    public String inscricoes() {
        return "inscricoes";
    }

    @GetMapping("/sessoes")
    public String sessoes() {
        return "sessoes";
    }

    @GetMapping("/certificados")
    public String certificados() {
        return "certificados";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
