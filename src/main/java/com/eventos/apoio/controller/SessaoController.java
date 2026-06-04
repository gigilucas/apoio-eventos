package com.eventos.apoio.controller;

import com.eventos.apoio.dto.SessaoDTO;
import com.eventos.apoio.mapper.SessaoMapper;
import com.eventos.apoio.model.Sessao;
import com.eventos.apoio.service.SessaoService;
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
@RequestMapping("/api/sessoes")
@Tag(name = "Sessões", description = "API para gestão de sessões de eventos")
public class SessaoController {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    public SessaoController(SessaoService sessaoService, SessaoMapper sessaoMapper) {
        this.sessaoService = sessaoService;
        this.sessaoMapper = sessaoMapper;
    }

    @PostMapping
    @Operation(summary = "Criar nova sessão", description = "Cria uma nova sessão para um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<SessaoDTO> criar(@Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoDTO);
        Sessao sessaoSalva = sessaoService.salvar(sessao);
        return ResponseEntity.ok(sessaoMapper.toDTO(sessaoSalva));
    }

    @GetMapping
    @Operation(summary = "Listar todas as sessões", description = "Retorna uma lista de todas as sessões")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso")
    })
    public ResponseEntity<List<SessaoDTO>> listar() {
        List<Sessao> sessoes = sessaoService.listarTodas();
        List<SessaoDTO> dtos = sessoes.stream()
                .map(sessaoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por ID", description = "Retorna uma sessão específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão encontrada"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
    })
    public ResponseEntity<SessaoDTO> buscarPorId(
            @Parameter(description = "ID da sessão") @PathVariable Integer id) {
        Sessao sessao = sessaoService.buscarPorId(id);
        return ResponseEntity.ok(sessaoMapper.toDTO(sessao));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sessão", description = "Atualiza os dados de uma sessão existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
    })
    public ResponseEntity<SessaoDTO> atualizar(
            @Parameter(description = "ID da sessão") @PathVariable Integer id,
            @Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoDTO);
        Sessao sessaoAtualizada = sessaoService.atualizar(id, sessao);
        return ResponseEntity.ok(sessaoMapper.toDTO(sessaoAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sessão", description = "Remove uma sessão pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sessão deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da sessão") @PathVariable Integer id) {
        sessaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}