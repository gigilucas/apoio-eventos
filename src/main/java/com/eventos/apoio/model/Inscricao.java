package com.eventos.apoio.model;

import com.eventos.apoio.enums.EstadoInscricao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inscricao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "DataInscricao", insertable = false, updatable = false)
    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoInscricao")
    private EstadoInscricao estadoInscricao;

    @ManyToOne
    @JoinColumn(name = "Participante_Id")
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "Evento_Id")
    private Evento evento;
}