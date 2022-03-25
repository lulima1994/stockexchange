package com.lucas.stockexchange.dto.carteira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CarteiraResponse {
    private String sigla;
    private Integer quantidade;
    private BigDecimal valorAtual;
    //private BigDecimal custoMedio; //valorAcao/quantidadeAcao
}
