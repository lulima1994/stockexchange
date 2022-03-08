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
    @Column
    private LocalDateTime dataHora;
    @Column(nullable = false)
    private BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "carteira_id")
    private Carteira carteira;
}
