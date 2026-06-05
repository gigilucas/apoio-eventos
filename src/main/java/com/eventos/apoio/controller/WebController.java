package com.eventos.apoio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping({"/", "/dashboard"})
    public String index() {
        return "index";
    }

    @GetMapping("/eventos")
    public String eventos() {
        return "eventos";
    }

    @GetMapping("/eventos/novo")
    public String novoEvento() {
        return "evento-novo";
    }

    @GetMapping("/participantes")
    public String participantes() {
        return "participantes";
    }

    @GetMapping("/participantes/novo")
    public String novoParticipante() {
        return "participante-novo";
    }

    @GetMapping("/inscricoes")
    public String inscricoes() {
        return "inscricoes";
    }

    @GetMapping("/inscricoes/nova")
    public String novaInscricao() {
        return "inscricao-nova";
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

    @PostMapping("/logout")
    public String logoutPost() {
        return "redirect:/login";
    }
}
