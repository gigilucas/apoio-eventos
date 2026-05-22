package com.eventos.apoio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sessao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // No teu print o 'id' está totalmente em minúsculas
    private Integer id;

    @Column(name = "Titulo")
    private String titulo;

    @Column(name = "NomeOrador")
    private String nomeOrador;

    @Column(name = "DataHoraInicio")
    private LocalDateTime dataHoraInicio;

    @Column(name = "DataHoraFim")
    private LocalDateTime dataHoraFim;

    // Relacionamento correto com a tua FK 'Evento_Id'
    @ManyToOne
    @JoinColumn(name = "Evento_Id")
    private Evento evento;
}