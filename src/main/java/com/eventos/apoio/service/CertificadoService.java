package com.eventos.apoio.service;

import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.repository.CertificadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }

    public Certificado emitir(Certificado certificado) {
        // Se não vier nenhum código, gera um automático e limpo de 8 caracteres
        if (certificado.getCodigoCertificado() == null || certificado.getCodigoCertificado().isEmpty()) {
            certificado.setCodigoCertificado("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        return certificadoRepository.save(certificado);
    }

    public List<Certificado> listarTodos() {
        return certificadoRepository.findAll();
    }
}