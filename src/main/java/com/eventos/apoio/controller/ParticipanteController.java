package com.eventos.apoio.controller;

import com.eventos.apoio.model.Participante;
import com.eventos.apoio.service.ParticipanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    // URL para Criar: POST http://localhost:8080/api/participantes
    @PostMapping
    public ResponseEntity<Participante> criar(@RequestBody Participante participante) {
        Participante novoParticipante = participanteService.salvar(participante);
        return ResponseEntity.ok(novoParticipante);
    }

    // URL para Listar: GET http://localhost:8080/api/participantes
    @GetMapping
    public ResponseEntity<List<Participante>> listar() {
        return ResponseEntity.ok(participanteService.listarTodos());
    }
}