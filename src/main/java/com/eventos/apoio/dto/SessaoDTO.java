package com.eventos.apoio.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDTO {
    private Integer id;

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 255, message = "O título deve ter entre 3 e 255 caracteres")
    private String titulo;

    @NotBlank(message = "O nome do orador é obrigatório")
    @Size(min = 3, max = 255, message = "O nome do orador deve ter entre 3 e 255 caracteres")
    private String nomeOrador;

    @NotNull(message = "A data e hora de início são obrigatórias")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "A data e hora de fim são obrigatórias")
    private LocalDateTime dataHoraFim;

    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;
}
