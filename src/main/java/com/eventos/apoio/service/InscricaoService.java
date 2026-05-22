package com.eventos.apoio.service;

import com.eventos.apoio.enums.EstadoInscricao; // <--- Importação da tua pasta enums!
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.repository.InscricaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }

    public Inscricao inscrever(Inscricao inscricao) {
        // Se a inscrição não tiver estado, fica Pendente por defeito
        if(inscricao.getEstadoInscricao() == null) {
            inscricao.setEstadoInscricao(EstadoInscricao.Pendente);
        }
        return inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }
}