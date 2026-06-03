package com.eventos.apoio.repository;

import com.eventos.apoio.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    long countByEventoId(Integer eventoId);
    boolean existsByParticipanteIdAndEventoId(Integer participanteId, Integer eventoId);
}