package com.eventos.apoio.controller;

import com.eventos.apoio.dto.CertificadoDTO;
import com.eventos.apoio.mapper.CertificadoMapper;
import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.service.CertificadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;
    private final CertificadoMapper certificadoMapper;

    public CertificadoController(CertificadoService certificadoService, CertificadoMapper certificadoMapper) {
        this.certificadoService = certificadoService;
        this.certificadoMapper = certificadoMapper;
    }

    @PostMapping
    public ResponseEntity<CertificadoDTO> emitir(@Valid @RequestBody CertificadoDTO certificadoDTO) {
        Certificado certificado = certificadoMapper.toEntity(certificadoDTO);
        Certificado certificadoSalvo = certificadoService.emitir(certificado);
        return ResponseEntity.ok(certificadoMapper.toDTO(certificadoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<CertificadoDTO>> listar() {
        List<Certificado> certificados = certificadoService.listarTodos();
        List<CertificadoDTO> dtos = certificados.stream()
                .map(certificadoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificadoDTO> buscarPorId(@PathVariable Integer id) {
        Certificado certificado = certificadoService.buscarPorId(id);
        return ResponseEntity.ok(certificadoMapper.toDTO(certificado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        certificadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}