package com.eventos.apoio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Participante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
    @Column(name = "Nome")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Column(name = "Email", unique = true)
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{9,15}$", message = "O telefone deve ter entre 9 e 15 dígitos")
    @Column(name = "Telefone")
    private String telefone;

    @Size(max = 50, message = "O número de estudante não pode ter mais de 50 caracteres")
    @Column(name = "NumeroEstudante")
    private String numeroEstudante;

    @Size(max = 255, message = "O curso não pode ter mais de 255 caracteres")
    @Column(name = "Curso")
    private String curso;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Column(name = "Password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private Role role = Role.USER;
}