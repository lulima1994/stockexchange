package com.lucas.stockexchange.dto.acao;

import com.lucas.stockexchange.domain.model.TipoAcao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AcaoResponse {
    private Long id;
    private String nome;
    private String sigla;
    private TipoAcao tipo;
    private String descricao;
    private Integer quantidade;
    private BigDecimal valor;
}
