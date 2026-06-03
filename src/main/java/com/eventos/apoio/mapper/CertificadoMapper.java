package com.eventos.apoio.mapper;

import com.eventos.apoio.dto.CertificadoDTO;
import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.repository.InscricaoRepository;
import org.springframework.stereotype.Component;

@Component
public class CertificadoMapper {

    private final InscricaoRepository inscricaoRepository;

    public CertificadoMapper(InscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }

    public CertificadoDTO toDTO(Certificado certificado) {
        if (certificado == null) {
            return null;
        }
        CertificadoDTO dto = new CertificadoDTO();
        dto.setId(certificado.getId());
        dto.setCodigoCertificado(certificado.getCodigoCertificado());
        dto.setDataEmissao(certificado.getDataEmissao());
        if (certificado.getInscricao() != null) {
            dto.setInscricaoId(certificado.getInscricao().getId());
        }
        return dto;
    }

    public Certificado toEntity(CertificadoDTO dto) {
        if (dto == null) {
            return null;
        }
        Certificado certificado = new Certificado();
        certificado.setId(dto.getId());
        certificado.setCodigoCertificado(dto.getCodigoCertificado());
        certificado.setDataEmissao(dto.getDataEmissao());
        
        if (dto.getInscricaoId() != null) {
            Inscricao inscricao = inscricaoRepository.findById(dto.getInscricaoId()).orElse(null);
            certificado.setInscricao(inscricao);
        }
        
        return certificado;
    }
}
