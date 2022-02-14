package com.lucas.stockexchange.dto.acao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AcaoRequest {
    private Long id;
    private String nome;
    private String sigla;
    private String tipo;
    private String descricao;
    private Integer quantidade;
}
