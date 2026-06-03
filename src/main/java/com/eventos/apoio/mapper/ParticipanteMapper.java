package com.eventos.apoio.mapper;

import com.eventos.apoio.dto.ParticipanteDTO;
import com.eventos.apoio.model.Participante;
import org.springframework.stereotype.Component;

@Component
public class ParticipanteMapper {

    public ParticipanteDTO toDTO(Participante participante) {
        if (participante == null) {
            return null;
        }
        ParticipanteDTO dto = new ParticipanteDTO();
        dto.setId(participante.getId());
        dto.setNome(participante.getNome());
        dto.setEmail(participante.getEmail());
        dto.setTelefone(participante.getTelefone());
        dto.setNumeroEstudante(participante.getNumeroEstudante());
        dto.setCurso(participante.getCurso());
        return dto;
    }

    public Participante toEntity(ParticipanteDTO dto) {
        if (dto == null) {
            return null;
        }
        Participante participante = new Participante();
        participante.setId(dto.getId());
        participante.setNome(dto.getNome());
        participante.setEmail(dto.getEmail());
        participante.setTelefone(dto.getTelefone());
        participante.setNumeroEstudante(dto.getNumeroEstudante());
        participante.setCurso(dto.getCurso());
        return participante;
    }
}
