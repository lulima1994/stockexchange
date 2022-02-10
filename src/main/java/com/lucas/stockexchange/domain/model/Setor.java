package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String nome;
    @Column(nullable = false, length = 500)
    private String descricao;
}
