package com.eventos.apoio.controller;

import com.eventos.apoio.dto.InscricaoDTO;
import com.eventos.apoio.mapper.InscricaoMapper;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;
    private final InscricaoMapper inscricaoMapper;

    public InscricaoController(InscricaoService inscricaoService, InscricaoMapper inscricaoMapper) {
        this.inscricaoService = inscricaoService;
        this.inscricaoMapper = inscricaoMapper;
    }

    @PostMapping
    public ResponseEntity<InscricaoDTO> inscrever(@Valid @RequestBody InscricaoDTO inscricaoDTO) {
        Inscricao inscricao = inscricaoMapper.toEntity(inscricaoDTO);
        Inscricao inscricaoSalva = inscricaoService.inscrever(inscricao);
        return ResponseEntity.ok(inscricaoMapper.toDTO(inscricaoSalva));
    }

    @GetMapping
    public ResponseEntity<List<InscricaoDTO>> listar() {
        List<Inscricao> inscricoes = inscricaoService.listarTodas();
        List<InscricaoDTO> dtos = inscricoes.stream()
                .map(inscricaoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoDTO> buscarPorId(@PathVariable Integer id) {
        Inscricao inscricao = inscricaoService.buscarPorId(id);
        return ResponseEntity.ok(inscricaoMapper.toDTO(inscricao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscricaoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody InscricaoDTO inscricaoDTO) {
        Inscricao inscricao = inscricaoMapper.toEntity(inscricaoDTO);
        Inscricao inscricaoAtualizada = inscricaoService.atualizar(id, inscricao);
        return ResponseEntity.ok(inscricaoMapper.toDTO(inscricaoAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        inscricaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}