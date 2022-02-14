package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String nome;
    @Column(nullable = false, length = 45)
    private String sigla;
    @Column
    private TipoAcao tipo;
    @Column(nullable = false, length = 500)
    private String descricao;
    @Column(nullable = false)
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
}
