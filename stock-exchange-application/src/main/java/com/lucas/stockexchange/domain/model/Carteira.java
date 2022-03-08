package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "acao_id")
    private Acao acao;
}
