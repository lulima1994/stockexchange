package com.lucas.stockexchange.service.mapper.operacao;

import com.lucas.stockexchange.domain.model.Operacao;
import com.lucas.stockexchange.dto.operacao.OperacaoResponse;

public class OperacaoResponseMapper {
    public OperacaoResponse mapear(Operacao operacao) {
        OperacaoResponse operacaoResponse = new OperacaoResponse();
        operacaoResponse.setId(operacaoResponse.getId());
        operacaoResponse.setDataHora(operacaoResponse.getDataHora());
        operacaoResponse.setValor(operacaoResponse.getValor());
        operacaoResponse.setCarteiraId(operacaoResponse.getCarteiraId());
        return operacaoResponse;
    }
}
