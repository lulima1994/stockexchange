package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Usuario;
import com.lucas.stockexchange.domain.repository.UsuarioRepository;
import com.lucas.stockexchange.dto.usuario.UsuarioRequest;
import com.lucas.stockexchange.dto.usuario.UsuarioResponse;
import com.lucas.stockexchange.service.mapper.usuario.UsuarioMapper;
import com.lucas.stockexchange.service.mapper.usuario.UsuarioResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioResponseMapper usuarioResponseMapper;

    public UsuarioResponse salvar(UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.mapear(usuarioRequest);
        usuarioRepository.save(usuario);
        UsuarioResponse usuarioResponse = usuarioResponseMapper.mapear(usuario);
        return usuarioResponse;
    }

    public UsuarioResponse buscarPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty())
            throw new RuntimeException("usuario " + id + " nao encontrado");
        UsuarioResponse usuarioResponse = usuarioResponseMapper.mapear(usuarioOptional.get());
        return usuarioResponse;
    }

    public Page<UsuarioResponse> buscarPorPagina(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        Page<UsuarioResponse> usuarioResponses = usuarios.map(usuarioResponseMapper::mapear);
        return usuarioResponses;
    }

    public void deletarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }
}
