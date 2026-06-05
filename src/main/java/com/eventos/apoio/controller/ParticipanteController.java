package com.eventos.apoio.controller;

import com.eventos.apoio.dto.ParticipanteDTO;
import com.eventos.apoio.mapper.ParticipanteMapper;
import com.eventos.apoio.model.Participante;
import com.eventos.apoio.service.ParticipanteService;
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

/**
 * Controller REST para gestão de participantes.
 * Fornece endpoints CRUD para criar, listar, buscar, atualizar e deletar participantes.
 */
@RestController
@RequestMapping("/api/participantes")
@Tag(name = "Participantes", description = "API para gestão de participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;
    private final ParticipanteMapper participanteMapper;

    /**
     * Construtor com injeção de dependências.
     * @param participanteService Serviço de participantes.
     * @param participanteMapper Mapper entre entidade e DTO.
     */
    public ParticipanteController(ParticipanteService participanteService, ParticipanteMapper participanteMapper) {
        this.participanteService = participanteService;
        this.participanteMapper = participanteMapper;
    }

    /**
     * Endpoint para criar um novo participante.
     * @param participanteDTO DTO com dados do novo participante.
     * @return ResponseEntity com o participante criado.
     */
    @PostMapping
    @Operation(summary = "Criar novo participante", description = "Cria um novo participante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ParticipanteDTO> criar(@Valid @RequestBody ParticipanteDTO participanteDTO) {
        Participante participante = participanteMapper.toEntity(participanteDTO);
        Participante participanteSalvo = participanteService.salvar(participante);
        return ResponseEntity.ok(participanteMapper.toDTO(participanteSalvo));
    }

    /**
     * Endpoint para listar todos os participantes.
     * @return ResponseEntity com lista de todos os participantes.
     */
    @GetMapping
    @Operation(summary = "Listar todos os participantes", description = "Retorna uma lista de todos os participantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de participantes retornada com sucesso")
    })
    public ResponseEntity<List<ParticipanteDTO>> listar() {
        List<Participante> participantes = participanteService.listarTodos();
        List<ParticipanteDTO> dtos = participantes.stream()
                .map(participanteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint para buscar um participante por ID.
     * @param id ID do participante.
     * @return ResponseEntity com o participante encontrado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar participante por ID", description = "Retorna um participante específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante encontrado"),
            @ApiResponse(responseCode = "404", description = "Participante não encontrado")
    })
    public ResponseEntity<ParticipanteDTO> buscarPorId(
            @Parameter(description = "ID do participante") @PathVariable Integer id) {
        Participante participante = participanteService.buscarPorId(id);
        return ResponseEntity.ok(participanteMapper.toDTO(participante));
    }

    /**
     * Endpoint para atualizar um participante existente.
     * @param id ID do participante a ser atualizado.
     * @param participanteDTO DTO com novos dados do participante.
     * @return ResponseEntity com o participante atualizado.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar participante", description = "Atualiza os dados de um participante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Participante não encontrado")
    })
    public ResponseEntity<ParticipanteDTO> atualizar(
            @Parameter(description = "ID do participante") @PathVariable Integer id,
            @Valid @RequestBody ParticipanteDTO participanteDTO) {
        Participante participante = participanteMapper.toEntity(participanteDTO);
        Participante participanteAtualizado = participanteService.atualizar(id, participante);
        return ResponseEntity.ok(participanteMapper.toDTO(participanteAtualizado));
    }

    /**
     * Endpoint para deletar um participante.
     * @param id ID do participante a ser deletado.
     * @return ResponseEntity sem conteúdo (204 No Content).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar participante", description = "Remove um participante pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Participante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Participante não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do participante") @PathVariable Integer id) {
        participanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}