package com.lucas.stockexchange.dto.operacao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class OperacaoRequest {
    private Long id;
    private BigDecimal valor;
    private Long carteiraId;
}
