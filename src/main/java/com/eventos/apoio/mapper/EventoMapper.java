package com.eventos.apoio.mapper;

import com.eventos.apoio.dto.EventoDTO;
import com.eventos.apoio.model.Evento;
import org.springframework.stereotype.Component;

@Component
public class EventoMapper {

    public EventoDTO toDTO(Evento evento) {
        if (evento == null) {
            return null;
        }
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setNome(evento.getNome());
        dto.setDescricao(evento.getDescricao());
        dto.setDataInicio(evento.getDataInicio());
        dto.setDataFim(evento.getDataFim());
        dto.setLocal(evento.getLocal());
        dto.setCapacidadeMaxima(evento.getCapacidadeMaxima());
        dto.setEstaAtivo(evento.getEstaAtivo());
        return dto;
    }

    public Evento toEntity(EventoDTO dto) {
        if (dto == null) {
            return null;
        }
        Evento evento = new Evento();
        evento.setId(dto.getId());
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setDataInicio(dto.getDataInicio());
        evento.setDataFim(dto.getDataFim());
        evento.setLocal(dto.getLocal());
        evento.setCapacidadeMaxima(dto.getCapacidadeMaxima());
        evento.setEstaAtivo(dto.getEstaAtivo());
        return evento;
    }
}
