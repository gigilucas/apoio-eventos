package com.eventos.apoio.model;

import com.eventos.apoio.enums.EstadoInscricao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "O estado da inscrição é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoInscricao")
    private EstadoInscricao estadoInscricao;

    @NotNull(message = "O participante é obrigatório")
    @ManyToOne
    @JoinColumn(name = "Participante_Id")
    private Participante participante;

    @NotNull(message = "O evento é obrigatório")
    @ManyToOne
    @JoinColumn(name = "Evento_Id")
    private Evento evento;
}