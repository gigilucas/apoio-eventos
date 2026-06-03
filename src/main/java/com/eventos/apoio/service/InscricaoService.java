package com.eventos.apoio.service;

import com.eventos.apoio.enums.EstadoInscricao;
import com.eventos.apoio.exception.BusinessException;
import com.eventos.apoio.exception.ResourceNotFoundException;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.repository.EventoRepository;
import com.eventos.apoio.repository.InscricaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventoRepository eventoRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
    }

    public Inscricao inscrever(Inscricao inscricao) {
        // Validar que o evento existe
        Evento evento = eventoRepository.findById(inscricao.getEvento().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento", inscricao.getEvento().getId()));

        // Validar capacidade máxima
        long numeroInscricoes = inscricaoRepository.countByEventoId(evento.getId());
        if (numeroInscricoes >= evento.getCapacidadeMaxima()) {
            throw new BusinessException("Erro: Evento esgotado! Capacidade máxima atingida.");
        }

        // Validar que o participante já não está inscrito neste evento
        boolean jaInscrito = inscricaoRepository.existsByParticipanteIdAndEventoId(
                inscricao.getParticipante().getId(),
                evento.getId()
        );
        if (jaInscrito) {
            throw new BusinessException("Erro: Participante já está inscrito neste evento.");
        }

        // Se a inscrição não tiver estado, fica Pendente por defeito
        if (inscricao.getEstadoInscricao() == null) {
            inscricao.setEstadoInscricao(EstadoInscricao.Pendente);
        }

        return inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    public Inscricao buscarPorId(Integer id) {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição", id));
    }

    public Inscricao atualizar(Integer id, Inscricao inscricao) {
        Inscricao inscricaoExistente = buscarPorId(id);
        inscricaoExistente.setEstadoInscricao(inscricao.getEstadoInscricao());
        inscricaoExistente.setParticipante(inscricao.getParticipante());
        inscricaoExistente.setEvento(inscricao.getEvento());
        return inscricaoRepository.save(inscricaoExistente);
    }

    public void deletar(Integer id) {
        Inscricao inscricao = buscarPorId(id);
        inscricaoRepository.delete(inscricao);
    }
}