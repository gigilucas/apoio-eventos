package com.eventos.apoio.controller;

import com.eventos.apoio.dto.SessaoDTO;
import com.eventos.apoio.mapper.SessaoMapper;
import com.eventos.apoio.model.Sessao;
import com.eventos.apoio.service.SessaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;
    private final SessaoMapper sessaoMapper;

    public SessaoController(SessaoService sessaoService, SessaoMapper sessaoMapper) {
        this.sessaoService = sessaoService;
        this.sessaoMapper = sessaoMapper;
    }

    @PostMapping
    public ResponseEntity<SessaoDTO> criar(@Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoDTO);
        Sessao sessaoSalva = sessaoService.salvar(sessao);
        return ResponseEntity.ok(sessaoMapper.toDTO(sessaoSalva));
    }

    @GetMapping
    public ResponseEntity<List<SessaoDTO>> listar() {
        List<Sessao> sessoes = sessaoService.listarTodas();
        List<SessaoDTO> dtos = sessoes.stream()
                .map(sessaoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDTO> buscarPorId(@PathVariable Integer id) {
        Sessao sessao = sessaoService.buscarPorId(id);
        return ResponseEntity.ok(sessaoMapper.toDTO(sessao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = sessaoMapper.toEntity(sessaoDTO);
        Sessao sessaoAtualizada = sessaoService.atualizar(id, sessao);
        return ResponseEntity.ok(sessaoMapper.toDTO(sessaoAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        sessaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}