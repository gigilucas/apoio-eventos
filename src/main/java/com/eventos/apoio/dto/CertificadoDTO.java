package com.eventos.apoio.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificadoDTO {
    private Integer id;

    @NotBlank(message = "O código do certificado é obrigatório")
    @Size(min = 5, max = 255, message = "O código deve ter entre 5 e 255 caracteres")
    private String codigoCertificado;

    private LocalDateTime dataEmissao;

    @NotNull(message = "O ID da inscrição é obrigatório")
    private Integer inscricaoId;
}
