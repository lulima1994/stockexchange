package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

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
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAcao tipo;
    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
}
