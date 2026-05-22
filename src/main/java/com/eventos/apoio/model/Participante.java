package com.eventos.apoio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Participante") // Garante que a tabela também bate certo com a maiúscula
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id") // <--- Força o "Id" com maiúscula
    private Integer id;

    @Column(name = "Nome") // <--- Força o "Nome" com maiúscula
    private String nome;

    @Column(name = "Email") // <--- Força o "Email" com maiúscula
    private String email;

    @Column(name = "Telefone") // <--- Força o "Telefone" com maiúscula
    private String telefone;

    @Column(name = "NumeroEstudante") // <--- Mapeia exatamente igual ao Workbench!
    private String numeroEstudante;

    @Column(name = "Curso") // <--- Força o "Curso" com maiúscula
    private String curso;
}