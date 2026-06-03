package com.eventos.apoio.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    private Integer id;

    @NotBlank(message = "O nome do evento é obrigatório")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
    private String nome;

    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    @Future(message = "A data de início deve ser no futuro")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDateTime dataFim;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "A capacidade máxima é obrigatória")
    @Positive(message = "A capacidade máxima deve ser positiva")
    private Integer capacidadeMaxima;

    @NotNull(message = "O estado ativo é obrigatório")
    private Boolean estaAtivo;
}
