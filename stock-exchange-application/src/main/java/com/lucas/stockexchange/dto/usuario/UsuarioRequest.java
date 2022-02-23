package com.lucas.stockexchange.dto.usuario;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioRequest {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private Integer cpf;
}
