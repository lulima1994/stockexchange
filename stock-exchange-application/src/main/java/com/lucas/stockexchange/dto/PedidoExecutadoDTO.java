package com.lucas.stockexchange.dto;

import com.lucas.stockexchange.domain.model.Acao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoExecutadoDTO {
    private Integer quantidade;
    private BigDecimal valorAcumulado;
    private Acao acao;

    public PedidoExecutadoDTO(Acao acao) {
        this.acao = acao;
        quantidade = 0;
        valorAcumulado = BigDecimal.ZERO;
    }
}
