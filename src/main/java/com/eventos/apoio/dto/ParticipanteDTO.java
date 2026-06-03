package com.eventos.apoio.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDTO {
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{9,15}$", message = "O telefone deve ter entre 9 e 15 dígitos")
    private String telefone;

    @Size(max = 50, message = "O número de estudante não pode ter mais de 50 caracteres")
    private String numeroEstudante;

    @Size(max = 255, message = "O curso não pode ter mais de 255 caracteres")
    private String curso;
}
