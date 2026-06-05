package com.eventos.apoio.service;

import com.eventos.apoio.enums.EstadoInscricao;
import com.eventos.apoio.exception.BusinessException;
import com.eventos.apoio.exception.ResourceNotFoundException;
import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.repository.CertificadoRepository;
import com.eventos.apoio.repository.InscricaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final InscricaoRepository inscricaoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository, InscricaoRepository inscricaoRepository) {
        this.certificadoRepository = certificadoRepository;
        this.inscricaoRepository = inscricaoRepository;
    }

    public Certificado emitir(Certificado certificado) {
        // Validar que a inscrição existe
        Inscricao inscricao = inscricaoRepository.findById(certificado.getInscricao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", certificado.getInscricao().getId()));

        // Validar que a inscrição está confirmada
        if (inscricao.getEstadoInscricao() != EstadoInscricao.Confirmada) {
            throw new BusinessException("Erro: Só é possível emitir certificados para inscrições confirmadas.");
        }

        // Validar que já não existe certificado para esta inscrição
        boolean certificadoExistente = certificadoRepository.existsByInscricaoId(inscricao.getId());
        if (certificadoExistente) {
            throw new BusinessException("Erro: Já existe um certificado para esta inscrição.");
        }

        // Se não vier nenhum código, gera um automático e limpo de 8 caracteres
        if (certificado.getCodigoCertificado() == null || certificado.getCodigoCertificado().isEmpty()) {
            certificado.setCodigoCertificado("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        return certificadoRepository.save(certificado);
    }

    public List<Certificado> listarTodos() {
        return certificadoRepository.findAll();
    }

    public Certificado buscarPorId(Integer id) {
        return certificadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificado", id));
    }

    public Certificado atualizar(Integer id, Certificado certificado) {
        Certificado certificadoExistente = buscarPorId(id);
        
        // Validar que a inscrição existe
        Inscricao inscricao = inscricaoRepository.findById(certificado.getInscricao().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", certificado.getInscricao().getId()));

        // Validar que a inscrição está confirmada
        if (inscricao.getEstadoInscricao() != EstadoInscricao.Confirmada) {
            throw new BusinessException("Erro: Só é possível emitir certificados para inscrições confirmadas.");
        }

        // Validar que já não existe certificado para esta inscrição (se mudou a inscrição)
        if (!certificadoExistente.getInscricao().getId().equals(inscricao.getId())) {
            boolean certificadoExistenteNovaInscricao = certificadoRepository.existsByInscricaoId(inscricao.getId());
            if (certificadoExistenteNovaInscricao) {
                throw new BusinessException("Erro: Já existe um certificado para esta inscrição.");
            }
        }

        certificadoExistente.setCodigoCertificado(certificado.getCodigoCertificado());
        certificadoExistente.setInscricao(inscricao);
        certificadoExistente.setDataEmissao(certificado.getDataEmissao());
        
        return certificadoRepository.save(certificadoExistente);
    }

    public void deletar(Integer id) {
        Certificado certificado = buscarPorId(id);
        certificadoRepository.delete(certificado);
    }
}