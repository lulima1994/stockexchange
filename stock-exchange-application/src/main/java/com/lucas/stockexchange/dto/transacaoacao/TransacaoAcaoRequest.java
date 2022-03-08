package com.lucas.stockexchange.dto.transacaoacao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransacaoAcaoRequest {
    private String login;
    private String sigla;
    private Integer quantidade;
}
