package com.lucas.stockexchange.dto.operacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OperacaoAcaoResponse {
    private String nome;
    private String sigla;
}
