package com.lucas.stockexchange.service.mapper.acao;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.dto.acao.AcaoRequest;
import org.springframework.stereotype.Component;

@Component
public class AcaoMapper {
    public Acao mapear(AcaoRequest acaoRequest) {
        Acao acao = new Acao();
        acao.setNome(acaoRequest.getNome());
        acao.setSigla(acaoRequest.getSigla());
        acao.setTipo(acaoRequest.getTipo());
        acao.setDescricao(acaoRequest.getDescricao());
        acao.setQuantidade(acaoRequest.getQuantidade());
        return acao;
    }
}
