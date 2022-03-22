package com.lucas.stockexchange.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class PedidoExecutadoTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataHoraRegistro;
    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
