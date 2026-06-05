package com.eventos.apoio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller responsável por gerir as rotas web da aplicação.
 * Serve as páginas HTML (Thymeleaf templates) para o frontend.
 */
@Controller
public class WebController {

    /**
     * Rota para a página de login.
     * @return Nome do template Thymeleaf para a página de login.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Rota para a página de registro de novos utilizadores.
     * @return Nome do template Thymeleaf para a página de registro.
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Rota para a página inicial (Dashboard).
     * Pode ser acedida via "/" ou "/dashboard".
     * @return Nome do template Thymeleaf para a página inicial.
     */
    @GetMapping({"/", "/dashboard"})
    public String index() {
        return "index";
    }

    /**
     * Rota para a página de listagem de eventos.
     * @return Nome do template Thymeleaf para a página de eventos.
     */
    @GetMapping("/eventos")
    public String eventos() {
        return "eventos";
    }

    /**
     * Rota para a página de criação de novos eventos.
     * @return Nome do template Thymeleaf para a página de novo evento.
     */
    @GetMapping("/eventos/novo")
    public String novoEvento() {
        return "evento-novo";
    }

    /**
     * Rota para a página de edição de eventos.
     * @return Nome do template Thymeleaf para a página de edição de evento.
     */
    @GetMapping("/eventos/editar")
    public String editarEvento() {
        return "evento-editar";
    }

    /**
     * Rota para a página de listagem de participantes.
     * @return Nome do template Thymeleaf para a página de participantes.
     */
    @GetMapping("/participantes")
    public String participantes() {
        return "participantes";
    }

    /**
     * Rota para a página de criação de novos participantes.
     * @return Nome do template Thymeleaf para a página de novo participante.
     */
    @GetMapping("/participantes/novo")
    public String novoParticipante() {
        return "participante-novo";
    }

    /**
     * Rota para a página de edição de participantes.
     * @return Nome do template Thymeleaf para a página de edição de participante.
     */
    @GetMapping("/participantes/editar")
    public String editarParticipante() {
        return "participante-editar";
    }

    /**
     * Rota para a página de listagem de inscrições.
     * @return Nome do template Thymeleaf para a página de inscrições.
     */
    @GetMapping("/inscricoes")
    public String inscricoes() {
        return "inscricoes";
    }

    /**
     * Rota para a página de criação de novas inscrições.
     * @return Nome do template Thymeleaf para a página de nova inscrição.
     */
    @GetMapping("/inscricoes/nova")
    public String novaInscricao() {
        return "inscricao-nova";
    }

    /**
     * Rota para a página de edição de inscrições.
     * @return Nome do template Thymeleaf para a página de edição de inscrição.
     */
    @GetMapping("/inscricoes/editar")
    public String editarInscricao() {
        return "inscricao-editar";
    }

    /**
     * Rota para a página de listagem de sessões.
     * @return Nome do template Thymeleaf para a página de sessões.
     */
    @GetMapping("/sessoes")
    public String sessoes() {
        return "sessoes";
    }

    /**
     * Rota para a página de criação de novas sessões.
     * @return Nome do template Thymeleaf para a página de nova sessão.
     */
    @GetMapping("/sessoes/nova")
    public String novaSessao() {
        return "sessao-nova";
    }

    /**
     * Rota para a página de edição de sessões.
     * @return Nome do template Thymeleaf para a página de edição de sessão.
     */
    @GetMapping("/sessoes/editar")
    public String editarSessao() {
        return "sessao-editar";
    }

    /**
     * Rota para a página de listagem de certificados.
     * @return Nome do template Thymeleaf para a página de certificados.
     */
    @GetMapping("/certificados")
    public String certificados() {
        return "certificados";
    }

    /**
     * Rota para a página de criação de novos certificados.
     * @return Nome do template Thymeleaf para a página de novo certificado.
     */
    @GetMapping("/certificados/novo")
    public String novoCertificado() {
        return "certificado-novo";
    }

    /**
     * Rota para a página de edição de certificados.
     * @return Nome do template Thymeleaf para a página de edição de certificado.
     */
    @GetMapping("/certificados/editar")
    public String editarCertificado() {
        return "certificado-editar";
    }

    /**
     * Rota GET para logout.
     * Redireciona para a página de login.
     * @return Redirecionamento para a página de login.
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    /**
     * Rota POST para logout.
     * Redireciona para a página de login.
     * @return Redirecionamento para a página de login.
     */
    @PostMapping("/logout")
    public String logoutPost() {
        return "redirect:/login";
    }
}
