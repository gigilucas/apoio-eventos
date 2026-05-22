package com.eventos.apoio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "DataInicio")
    private LocalDateTime dataInicio;

    @Column(name = "DataFim")
    private LocalDateTime dataFim;

    @Column(name = "Local")
    private String local;

    @Column(name = "CapacidadeMaxima")
    private Integer capacidadeMaxima;

    @Column(name = "EstaAtivo")
    private Boolean estaAtivo;
}