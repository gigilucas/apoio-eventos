package com.eventos.apoio.mapper;

import com.eventos.apoio.dto.EventoDTO;
import com.eventos.apoio.dto.InscricaoDTO;
import com.eventos.apoio.dto.ParticipanteDTO;
import com.eventos.apoio.enums.EstadoInscricao;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.model.Participante;
import com.eventos.apoio.repository.EventoRepository;
import com.eventos.apoio.repository.ParticipanteRepository;
import org.springframework.stereotype.Component;

@Component
public class InscricaoMapper {

    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final EventoMapper eventoMapper;
    private final ParticipanteMapper participanteMapper;

    public InscricaoMapper(EventoRepository eventoRepository, 
                          ParticipanteRepository participanteRepository,
                          EventoMapper eventoMapper,
                          ParticipanteMapper participanteMapper) {
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
        this.eventoMapper = eventoMapper;
        this.participanteMapper = participanteMapper;
    }

    public InscricaoDTO toDTO(Inscricao inscricao) {
        if (inscricao == null) {
            return null;
        }
        InscricaoDTO dto = new InscricaoDTO();
        dto.setId(inscricao.getId());
        dto.setDataInscricao(inscricao.getDataInscricao());
        dto.setEstadoInscricao(inscricao.getEstadoInscricao());
        if (inscricao.getParticipante() != null) {
            dto.setParticipanteId(inscricao.getParticipante().getId());
            dto.setParticipante(participanteMapper.toDTO(inscricao.getParticipante()));
        }
        if (inscricao.getEvento() != null) {
            dto.setEventoId(inscricao.getEvento().getId());
            dto.setEvento(eventoMapper.toDTO(inscricao.getEvento()));
        }
        return dto;
    }

    public Inscricao toEntity(InscricaoDTO dto) {
        if (dto == null) {
            return null;
        }
        Inscricao inscricao = new Inscricao();
        inscricao.setId(dto.getId());
        inscricao.setDataInscricao(dto.getDataInscricao());
        inscricao.setEstadoInscricao(dto.getEstadoInscricao() != null ? dto.getEstadoInscricao() : EstadoInscricao.Pendente);
        
        if (dto.getParticipanteId() != null) {
            Participante participante = participanteRepository.findById(dto.getParticipanteId()).orElse(null);
            inscricao.setParticipante(participante);
        }
        
        if (dto.getEventoId() != null) {
            Evento evento = eventoRepository.findById(dto.getEventoId()).orElse(null);
            inscricao.setEvento(evento);
        }
        
        return inscricao;
    }
}
