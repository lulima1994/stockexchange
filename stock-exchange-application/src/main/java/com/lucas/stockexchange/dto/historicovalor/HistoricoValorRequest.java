package com.lucas.stockexchange.dto.historicovalor;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class HistoricoValorRequest {
    private BigDecimal valor;
    private Long acaoId;
}
