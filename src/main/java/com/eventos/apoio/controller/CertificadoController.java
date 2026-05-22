package com.eventos.apoio.controller;

import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.service.CertificadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @PostMapping
    public ResponseEntity<Certificado> emitir(@RequestBody Certificado certificado) {
        return ResponseEntity.ok(certificadoService.emitir(certificado));
    }

    @GetMapping
    public ResponseEntity<List<Certificado>> listar() {
        return ResponseEntity.ok(certificadoService.listarTodos());
    }
}