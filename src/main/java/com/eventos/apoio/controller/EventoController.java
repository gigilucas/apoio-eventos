package com.eventos.apoio.controller;

import com.eventos.apoio.dto.EventoDTO;
import com.eventos.apoio.mapper.EventoMapper;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Eventos", description = "API para gestão de eventos académicos")
public class EventoController {

    private final EventoService eventoService;
    private final EventoMapper eventoMapper;

    public EventoController(EventoService eventoService, EventoMapper eventoMapper) {
        this.eventoService = eventoService;
        this.eventoMapper = eventoMapper;
    }

    @PostMapping
    @Operation(summary = "Criar novo evento", description = "Cria um novo evento académico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<EventoDTO> criar(@Valid @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoMapper.toEntity(eventoDTO);
        Evento eventoSalvo = eventoService.salvar(evento);
        return ResponseEntity.ok(eventoMapper.toDTO(eventoSalvo));
    }

    @GetMapping
    @Operation(summary = "Listar todos os eventos", description = "Retorna uma lista de todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso")
    })
    public ResponseEntity<List<EventoDTO>> listar() {
        List<Evento> eventos = eventoService.listarTodos();
        List<EventoDTO> dtos = eventos.stream()
                .map(eventoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID", description = "Retorna um evento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<EventoDTO> buscarPorId(
            @Parameter(description = "ID do evento") @PathVariable Integer id) {
        Evento evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(eventoMapper.toDTO(evento));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evento", description = "Atualiza os dados de um evento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<EventoDTO> atualizar(
            @Parameter(description = "ID do evento") @PathVariable Integer id,
            @Valid @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoMapper.toEntity(eventoDTO);
        Evento eventoAtualizado = eventoService.atualizar(id, evento);
        return ResponseEntity.ok(eventoMapper.toDTO(eventoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento", description = "Remove um evento pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do evento") @PathVariable Integer id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}