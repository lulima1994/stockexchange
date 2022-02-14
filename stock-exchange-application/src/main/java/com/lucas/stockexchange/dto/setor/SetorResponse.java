package com.lucas.stockexchange.dto.setor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SetorResponse {
    private Long id;
    private String nome;
    private String descricao;
}
