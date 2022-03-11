package com.lucas.stockexchange.dto.transacaoacao;

import com.lucas.stockexchange.domain.model.TipoOperacao;
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
public class TransacaoAcaoResponse {
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    private BigDecimal valorUnitario;
    private Integer quantidadeTransacao;
    private Integer quantidadeCarteira;
    private TipoOperacao tipo;
}
