package com.eventos.apoio.service;

import com.eventos.apoio.exception.ResourceNotFoundException;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.repository.EventoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento salvar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }

    public Evento atualizar(Integer id, Evento evento) {
        Evento eventoExistente = buscarPorId(id);
        eventoExistente.setNome(evento.getNome());
        eventoExistente.setDescricao(evento.getDescricao());
        eventoExistente.setDataInicio(evento.getDataInicio());
        eventoExistente.setDataFim(evento.getDataFim());
        eventoExistente.setLocal(evento.getLocal());
        eventoExistente.setCapacidadeMaxima(evento.getCapacidadeMaxima());
        eventoExistente.setEstaAtivo(evento.getEstaAtivo());
        return eventoRepository.save(eventoExistente);
    }

    public void deletar(Integer id) {
        Evento evento = buscarPorId(id);
        eventoRepository.delete(evento);
    }
}