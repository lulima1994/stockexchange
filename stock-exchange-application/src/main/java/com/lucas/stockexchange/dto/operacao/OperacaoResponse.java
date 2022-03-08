package com.lucas.stockexchange.dto.operacao;

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
public class OperacaoResponse {
    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valor;
    private Long carteiraId;
}
