package com.eventos.apoio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Certificado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Força a olhar para o 'id' minúsculo do Workbench
    private Integer id;

    @Column(name = "codigoCertificado") // Mapeia direto para a tua coluna
    private String codigoCertificado;

    @Column(name = "DataEmissao", insertable = false, updatable = false)
    // Mapeia para o teu 'DataEmissao' real e deixa o MySQL gerar o TIMESTAMP
    private LocalDateTime dataEmissao;

    @OneToOne
    @JoinColumn(name = "Inscricao_Id") // Tem de ser igualzinho ao teu print: 'Inscricao_Id'
    private Inscricao inscricao;
}