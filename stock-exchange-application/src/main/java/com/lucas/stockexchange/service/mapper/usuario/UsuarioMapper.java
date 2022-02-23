package com.lucas.stockexchange.service.mapper.usuario;

import com.lucas.stockexchange.domain.model.Usuario;
import com.lucas.stockexchange.dto.usuario.UsuarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {
    public Usuario mapear(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setLogin(usuarioRequest.getLogin());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setCpf(usuarioRequest.getCpf());
        return usuario;
    }
}
