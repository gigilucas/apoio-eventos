package com.eventos.apoio.controller;

import com.eventos.apoio.dto.CertificadoDTO;
import com.eventos.apoio.mapper.CertificadoMapper;
import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.service.CertificadoService;
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
@RequestMapping("/api/certificados")
@Tag(name = "Certificados", description = "API para gestão de certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;
    private final CertificadoMapper certificadoMapper;

    public CertificadoController(CertificadoService certificadoService, CertificadoMapper certificadoMapper) {
        this.certificadoService = certificadoService;
        this.certificadoMapper = certificadoMapper;
    }

    @PostMapping
    @Operation(summary = "Emitir certificado", description = "Emite um novo certificado para uma inscrição confirmada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificado emitido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou inscrição não confirmada")
    })
    public ResponseEntity<CertificadoDTO> emitir(@Valid @RequestBody CertificadoDTO certificadoDTO) {
        Certificado certificado = certificadoMapper.toEntity(certificadoDTO);
        Certificado certificadoSalvo = certificadoService.emitir(certificado);
        return ResponseEntity.ok(certificadoMapper.toDTO(certificadoSalvo));
    }

    @GetMapping
    @Operation(summary = "Listar todos os certificados", description = "Retorna uma lista de todos os certificados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de certificados retornada com sucesso")
    })
    public ResponseEntity<List<CertificadoDTO>> listar() {
        List<Certificado> certificados = certificadoService.listarTodos();
        List<CertificadoDTO> dtos = certificados.stream()
                .map(certificadoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar certificado por ID", description = "Retorna um certificado específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificado encontrado"),
            @ApiResponse(responseCode = "404", description = "Certificado não encontrado")
    })
    public ResponseEntity<CertificadoDTO> buscarPorId(
            @Parameter(description = "ID do certificado") @PathVariable Integer id) {
        Certificado certificado = certificadoService.buscarPorId(id);
        return ResponseEntity.ok(certificadoMapper.toDTO(certificado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar certificado", description = "Remove um certificado pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Certificado deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Certificado não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do certificado") @PathVariable Integer id) {
        certificadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}