package com.lucas.stockexchange.dto.transacaoacao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class TransacaoAcaoRequest {
    private String login;
    private String sigla;
    private BigDecimal valor;
    private Integer quantidade;
    private LocalDateTime prazo;
}
