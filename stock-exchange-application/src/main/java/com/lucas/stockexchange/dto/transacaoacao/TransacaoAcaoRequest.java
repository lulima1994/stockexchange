package com.lucas.stockexchange.dto.transacaoacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TransacaoAcaoRequest {
    private String login;
    private String sigla;
    private BigDecimal valor;
    private Integer quantidade;
    private LocalDateTime prazo;
}
