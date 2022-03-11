package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Operacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataHora;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoOperacao tipo;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "acao_id")
    private Acao acao;
}
