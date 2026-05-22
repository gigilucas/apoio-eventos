package com.eventos.apoio.service;

import com.eventos.apoio.model.Participante;
import com.eventos.apoio.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    // O Spring injeta o Repository automaticamente aqui através do construtor
    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    // Método para guardar um participante no MySQL
    public Participante salvar(Participante participante) {
        // Aqui podias meter regras, ex: validar se o email já existe
        return participanteRepository.save(participante);
    }

    // Método para listar todos os participantes
    public List<Participante> listarTodos() {
        return participanteRepository.findAll();
    }
}
