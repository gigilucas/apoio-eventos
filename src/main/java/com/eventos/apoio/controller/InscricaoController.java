package com.eventos.apoio.controller;

import com.eventos.apoio.dto.InscricaoDTO;
import com.eventos.apoio.mapper.InscricaoMapper;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.service.InscricaoService;
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
@RequestMapping("/api/inscricoes")
@Tag(name = "Inscrições", description = "API para gestão de inscrições em eventos")
public class InscricaoController {

    private final InscricaoService inscricaoService;
    private final InscricaoMapper inscricaoMapper;

    public InscricaoController(InscricaoService inscricaoService, InscricaoMapper inscricaoMapper) {
        this.inscricaoService = inscricaoService;
        this.inscricaoMapper = inscricaoMapper;
    }

    @PostMapping
    @Operation(summary = "Criar inscrição", description = "Cria uma nova inscrição em um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada")
    })
    public ResponseEntity<InscricaoDTO> inscrever(@Valid @RequestBody InscricaoDTO inscricaoDTO) {
        Inscricao inscricao = inscricaoMapper.toEntity(inscricaoDTO);
        Inscricao inscricaoSalva = inscricaoService.inscrever(inscricao);
        return ResponseEntity.ok(inscricaoMapper.toDTO(inscricaoSalva));
    }

    @GetMapping
    @Operation(summary = "Listar todas as inscrições", description = "Retorna uma lista de todas as inscrições")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de inscrições retornada com sucesso")
    })
    public ResponseEntity<List<InscricaoDTO>> listar() {
        List<Inscricao> inscricoes = inscricaoService.listarTodas();
        List<InscricaoDTO> dtos = inscricoes.stream()
                .map(inscricaoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inscrição por ID", description = "Retorna uma inscrição específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição encontrada"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada")
    })
    public ResponseEntity<InscricaoDTO> buscarPorId(
            @Parameter(description = "ID da inscrição") @PathVariable Integer id) {
        Inscricao inscricao = inscricaoService.buscarPorId(id);
        return ResponseEntity.ok(inscricaoMapper.toDTO(inscricao));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar inscrição", description = "Atualiza o estado de uma inscrição existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada")
    })
    public ResponseEntity<InscricaoDTO> atualizar(
            @Parameter(description = "ID da inscrição") @PathVariable Integer id,
            @Valid @RequestBody InscricaoDTO inscricaoDTO) {
        Inscricao inscricao = inscricaoMapper.toEntity(inscricaoDTO);
        Inscricao inscricaoAtualizada = inscricaoService.atualizar(id, inscricao);
        return ResponseEntity.ok(inscricaoMapper.toDTO(inscricaoAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar inscrição", description = "Remove uma inscrição pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inscrição deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da inscrição") @PathVariable Integer id) {
        inscricaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}