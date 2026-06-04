package com.eventos.apoio.repository;

import com.eventos.apoio.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    Optional<Participante> findByEmail(String email);
    boolean existsByEmail(String email);
}