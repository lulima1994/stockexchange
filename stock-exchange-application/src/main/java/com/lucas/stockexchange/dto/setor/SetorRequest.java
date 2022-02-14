package com.lucas.stockexchange.dto.setor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SetorRequest {
    private Long id;
    private String nome;
    private String descricao;
}
