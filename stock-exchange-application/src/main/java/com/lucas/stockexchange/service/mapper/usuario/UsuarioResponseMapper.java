package com.lucas.stockexchange.service.mapper.usuario;

import com.lucas.stockexchange.domain.model.Usuario;
import com.lucas.stockexchange.dto.usuario.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioResponseMapper {
    public UsuarioResponse mapear(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setLogin(usuario.getLogin());
        usuarioResponse.setCpf(usuario.getCpf());
        return usuarioResponse;
    }
}
