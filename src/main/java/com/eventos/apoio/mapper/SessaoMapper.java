package com.eventos.apoio.mapper;

import com.eventos.apoio.dto.SessaoDTO;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.model.Sessao;
import com.eventos.apoio.repository.EventoRepository;
import org.springframework.stereotype.Component;

@Component
public class SessaoMapper {

    private final EventoRepository eventoRepository;

    public SessaoMapper(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public SessaoDTO toDTO(Sessao sessao) {
        if (sessao == null) {
            return null;
        }
        SessaoDTO dto = new SessaoDTO();
        dto.setId(sessao.getId());
        dto.setTitulo(sessao.getTitulo());
        dto.setNomeOrador(sessao.getNomeOrador());
        dto.setDataHoraInicio(sessao.getDataHoraInicio());
        dto.setDataHoraFim(sessao.getDataHoraFim());
        if (sessao.getEvento() != null) {
            dto.setEventoId(sessao.getEvento().getId());
        }
        return dto;
    }

    public Sessao toEntity(SessaoDTO dto) {
        if (dto == null) {
            return null;
        }
        Sessao sessao = new Sessao();
        sessao.setId(dto.getId());
        sessao.setTitulo(dto.getTitulo());
        sessao.setNomeOrador(dto.getNomeOrador());
        sessao.setDataHoraInicio(dto.getDataHoraInicio());
        sessao.setDataHoraFim(dto.getDataHoraFim());
        
        if (dto.getEventoId() != null) {
            Evento evento = eventoRepository.findById(dto.getEventoId()).orElse(null);
            sessao.setEvento(evento);
        }
        
        return sessao;
    }
}
