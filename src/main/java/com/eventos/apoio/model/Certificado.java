package com.eventos.apoio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Certificado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "O código do certificado é obrigatório")
    @Size(min = 5, max = 255, message = "O código deve ter entre 5 e 255 caracteres")
    @Column(name = "codigoCertificado")
    private String codigoCertificado;

    @Column(name = "DataEmissao", insertable = false, updatable = false)
    private LocalDateTime dataEmissao;

    @NotNull(message = "A inscrição é obrigatória")
    @OneToOne
    @JoinColumn(name = "Inscricao_Id")
    private Inscricao inscricao;
}