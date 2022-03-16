package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Operacao;
import com.lucas.stockexchange.domain.repository.OperacaoRepository;
import com.lucas.stockexchange.dto.operacao.OperacaoAcaoResponse;
import com.lucas.stockexchange.dto.operacao.OperacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;

    public Page<OperacaoResponse> buscarPorLogin(String login, Pageable pageable) {
        Page<Operacao> operacaos = operacaoRepository.findByLogin(login, pageable);
        return operacaos.map((cadaOperacao) -> {
            OperacaoResponse operacaoResponse = new OperacaoResponse();
            OperacaoAcaoResponse operacaoAcaoResponse = new OperacaoAcaoResponse();
            operacaoResponse.setId(cadaOperacao.getId());
            operacaoResponse.setDataHora(cadaOperacao.getDataHora());
            operacaoResponse.setValor(cadaOperacao.getValor());
            operacaoResponse.setQuantidade(cadaOperacao.getQuantidade());
            operacaoResponse.setTipo(cadaOperacao.getTipo());
            operacaoAcaoResponse.setNome(cadaOperacao.getAcao().getNome());
            operacaoAcaoResponse.setSigla(cadaOperacao.getAcao().getSigla());
            operacaoResponse.setAcao(operacaoAcaoResponse);
            return operacaoResponse;
        });
    }
}
