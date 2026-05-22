package com.eventos.apoio.service;

import com.eventos.apoio.model.Sessao;
import com.eventos.apoio.repository.SessaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    public SessaoService(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    public Sessao salvar(Sessao sessao) {
        return sessaoRepository.save(sessao);
    }

    public List<Sessao> listarTodas() {
        return sessaoRepository.findAll();
    }
}