package com.lucas.stockexchange.service.mapper.operacao;

import com.lucas.stockexchange.domain.model.Operacao;
import com.lucas.stockexchange.domain.repository.CarteiraRepository;
import com.lucas.stockexchange.dto.operacao.OperacaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperacaoMapper {
    private final CarteiraRepository carteiraRepository;

    public Operacao mapear(OperacaoRequest operacaoRequest) {
        Operacao operacao = new Operacao();
        operacao.setId(operacaoRequest.getId());
        operacao.setValor(operacaoRequest.getValor());
        operacao.setCarteira(carteiraRepository.findById(operacaoRequest.getCarteiraId()).orElseThrow());
        return operacao;
    }
}
