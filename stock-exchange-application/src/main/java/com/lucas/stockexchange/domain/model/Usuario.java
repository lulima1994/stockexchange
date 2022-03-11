package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String nome;
    @Column(nullable = false, length = 45)
    private String login;
    @Column(nullable = false, length = 45)
    private String senha;
    @Column(nullable = false, length = 45)
    private String cpf;
}