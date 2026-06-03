package com.eventos.apoio.controller;

import com.eventos.apoio.dto.EventoDTO;
import com.eventos.apoio.mapper.EventoMapper;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final EventoMapper eventoMapper;

    public EventoController(EventoService eventoService, EventoMapper eventoMapper) {
        this.eventoService = eventoService;
        this.eventoMapper = eventoMapper;
    }

    @PostMapping
    public ResponseEntity<EventoDTO> criar(@Valid @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoMapper.toEntity(eventoDTO);
        Evento eventoSalvo = eventoService.salvar(evento);
        return ResponseEntity.ok(eventoMapper.toDTO(eventoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listar() {
        List<Evento> eventos = eventoService.listarTodos();
        List<EventoDTO> dtos = eventos.stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Integer id) {
        Evento evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoMapper.toEntity(eventoDTO);
        Evento eventoAtualizado = eventoService.atualizar(id, evento);
        return ResponseEntity.ok(eventoMapper.toDTO(eventoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}