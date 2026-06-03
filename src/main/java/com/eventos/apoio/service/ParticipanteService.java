package com.eventos.apoio.service;

import com.eventos.apoio.exception.ResourceNotFoundException;
import com.eventos.apoio.model.Participante;
import com.eventos.apoio.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    public Participante salvar(Participante participante) {
        return participanteRepository.save(participante);
    }

    public List<Participante> listarTodos() {
        return participanteRepository.findAll();
    }

    public Participante buscarPorId(Integer id) {
        return participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante", id));
    }

    public Participante atualizar(Integer id, Participante participante) {
        Participante participanteExistente = buscarPorId(id);
        participanteExistente.setNome(participante.getNome());
        participanteExistente.setEmail(participante.getEmail());
        participanteExistente.setTelefone(participante.getTelefone());
        participanteExistente.setNumeroEstudante(participante.getNumeroEstudante());
        participanteExistente.setCurso(participante.getCurso());
        return participanteRepository.save(participanteExistente);
    }

    public void deletar(Integer id) {
        Participante participante = buscarPorId(id);
        participanteRepository.delete(participante);
    }
}
