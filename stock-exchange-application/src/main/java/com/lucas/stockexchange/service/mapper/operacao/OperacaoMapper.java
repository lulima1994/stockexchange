package com.lucas.stockexchange.service.mapper.operacao;

import com.lucas.stockexchange.domain.model.Operacao;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.UsuarioRepository;
import com.lucas.stockexchange.dto.operacao.OperacaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperacaoMapper {
    private final UsuarioRepository usuarioRepository;
    private final AcaoRepository acaoRepository;

    public Operacao mapear(OperacaoRequest operacaoRequest) {
        Operacao operacao = new Operacao();
        operacao.setId(operacaoRequest.getId());
        operacao.setValor(operacaoRequest.getValor());
        operacao.setQuantidade(operacaoRequest.getQuantidade());
        operacao.setUsuario(usuarioRepository.findById(operacaoRequest.getUsuarioId()).orElseThrow());
        operacao.setAcao(acaoRepository.findById(operacaoRequest.getAcaoId()).orElseThrow());
        return operacao;
    }
}
