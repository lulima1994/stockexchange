package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @Column(nullable = false, length = 45)
    private String tipo;
    @Column(nullable = false, length = 500)
    private String descricao;
    @Column(nullable = false)
    private Integer quantidade;
    @OneToMany(mappedBy = "acao")
    private List<HistoricoValor> historicoValores;
}
