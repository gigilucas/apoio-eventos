package com.eventos.apoio.controller;

import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.service.InscricaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping
    public ResponseEntity<Inscricao> inscrever(@RequestBody Inscricao inscricao) {
        return ResponseEntity.ok(inscricaoService.inscrever(inscricao));
    }

    @GetMapping
    public ResponseEntity<List<Inscricao>> listar() {
        return ResponseEntity.ok(inscricaoService.listarTodas());
    }
}