package com.eventos.apoio.controller;

import com.eventos.apoio.dto.ParticipanteDTO;
import com.eventos.apoio.mapper.ParticipanteMapper;
import com.eventos.apoio.model.Participante;
import com.eventos.apoio.service.ParticipanteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;
    private final ParticipanteMapper participanteMapper;

    public ParticipanteController(ParticipanteService participanteService, ParticipanteMapper participanteMapper) {
        this.participanteService = participanteService;
        this.participanteMapper = participanteMapper;
    }

    @PostMapping
    public ResponseEntity<ParticipanteDTO> criar(@Valid @RequestBody ParticipanteDTO participanteDTO) {
        Participante participante = participanteMapper.toEntity(participanteDTO);
        Participante participanteSalvo = participanteService.salvar(participante);
        return ResponseEntity.ok(participanteMapper.toDTO(participanteSalvo));
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> listar() {
        List<Participante> participantes = participanteService.listarTodos();
        List<ParticipanteDTO> dtos = participantes.stream()
                .map(participanteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> buscarPorId(@PathVariable Integer id) {
        Participante participante = participanteService.buscarPorId(id);
        return ResponseEntity.ok(participanteMapper.toDTO(participante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody ParticipanteDTO participanteDTO) {
        Participante participante = participanteMapper.toEntity(participanteDTO);
        Participante participanteAtualizado = participanteService.atualizar(id, participante);
        return ResponseEntity.ok(participanteMapper.toDTO(participanteAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        participanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}