package com.lucas.stockexchange.dto.acao;

import com.lucas.stockexchange.domain.model.TipoAcao;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AcaoRequest {
    private Long id;
    private String nome;
    private String sigla;
    private TipoAcao tipo;
    private String descricao;
    private Integer quantidade;
    private Long setorId;
    private BigDecimal valor;
}
