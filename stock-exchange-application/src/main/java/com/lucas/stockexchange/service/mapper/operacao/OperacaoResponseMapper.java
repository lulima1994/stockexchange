package com.lucas.stockexchange.service.mapper.operacao;

import com.lucas.stockexchange.domain.model.Operacao;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.UsuarioRepository;
import com.lucas.stockexchange.dto.operacao.OperacaoResponse;
import com.lucas.stockexchange.service.mapper.acao.AcaoResponseMapper;
import com.lucas.stockexchange.service.mapper.usuario.UsuarioResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperacaoResponseMapper {
    private final UsuarioResponseMapper usuarioResponseMapper;
    private final AcaoResponseMapper acaoResponseMapper;
    public OperacaoResponse mapear(Operacao operacao){
        OperacaoResponse operacaoResponse = new OperacaoResponse();
        operacaoResponse.setId(operacao.getId());
        operacaoResponse.setDataHora(operacao.getDataHora());
        operacaoResponse.setValor(operacao.getValor());
        operacaoResponse.setQuantidade(operacaoResponse.getQuantidade());
        operacaoResponse.setTipo(operacao.getTipo());
        operacaoResponse.setUsuarioResponse(usuarioResponseMapper.mapear(operacao.getUsuario()));
        operacaoResponse.setAcaoResponse(acaoResponseMapper.mapear(operacao.getAcao()));
        return operacaoResponse;
    }
}
