package com.eventos.apoio.controller;

import com.eventos.apoio.model.Sessao;
import com.eventos.apoio.service.SessaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<Sessao> criar(@RequestBody Sessao sessao) {
        return ResponseEntity.ok(sessaoService.salvar(sessao));
    }

    @GetMapping
    public ResponseEntity<List<Sessao>> listar() {
        return ResponseEntity.ok(sessaoService.listarTodas());
    }
}