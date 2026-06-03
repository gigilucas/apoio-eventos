package com.eventos.apoio.dto;

import com.eventos.apoio.enums.EstadoInscricao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoDTO {
    private Integer id;
    private LocalDateTime dataInscricao;

    @NotNull(message = "O estado da inscrição é obrigatório")
    private EstadoInscricao estadoInscricao;

    @NotNull(message = "O ID do participante é obrigatório")
    private Integer participanteId;

    @NotNull(message = "O ID do evento é obrigatório")
    private Integer eventoId;
}
