package com.lucas.stockexchange.service.mapper.acao;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import org.springframework.stereotype.Component;

@Component
public class AcaoResponseMapper {
    public AcaoResponse mapear(Acao acao) {
        AcaoResponse acaoResponse = new AcaoResponse();
        acaoResponse.setId(acao.getId());
        acaoResponse.setNome(acao.getNome());
        acaoResponse.setSigla(acao.getSigla());
        acaoResponse.setTipo(acao.getTipo());
        acaoResponse.setDescricao(acao.getDescricao());
        acaoResponse.setQuantidade(acao.getQuantidade());
        return acaoResponse;
    }
}
