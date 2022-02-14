package com.lucas.stockexchange.dto.historicovalor;

import com.lucas.stockexchange.dto.acao.AcaoResponse;
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
public class HistoricoValorResponse {
    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valor;
    private AcaoResponse acaoResponse;
}
