package com.eventos.apoio.service;

import com.eventos.apoio.exception.ResourceNotFoundException;
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

    public Sessao buscarPorId(Integer id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão", id));
    }

    public Sessao atualizar(Integer id, Sessao sessao) {
        Sessao sessaoExistente = buscarPorId(id);
        sessaoExistente.setTitulo(sessao.getTitulo());
        sessaoExistente.setNomeOrador(sessao.getNomeOrador());
        sessaoExistente.setDataHoraInicio(sessao.getDataHoraInicio());
        sessaoExistente.setDataHoraFim(sessao.getDataHoraFim());
        sessaoExistente.setEvento(sessao.getEvento());
        return sessaoRepository.save(sessaoExistente);
    }

    public void deletar(Integer id) {
        Sessao sessao = buscarPorId(id);
        sessaoRepository.delete(sessao);
    }
}