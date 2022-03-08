package com.lucas.stockexchange.dto.carteira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CarteiraResponse {
    private Long id;
    private Integer quantidade;
    private Long usuarioId;
    private Long acaoId;
}
