package com.lucas.stockexchange.dto.carteira;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarteiraRequest {
    private Long id;
    private Integer quantidade;
    private Long usuarioId;
    private Long acaoId;
}
