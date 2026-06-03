package com.eventos.apoio.repository;

import com.eventos.apoio.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
}