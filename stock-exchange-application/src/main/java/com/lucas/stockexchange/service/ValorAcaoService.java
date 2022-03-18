package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.repository.HistoricoValorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class ValorAcaoService {
    private final HistoricoValorRepository historicoValorRepository;

    public BigDecimal valorAtual(String sigla) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "dataHora");
        return historicoValorRepository.findBySigla(sigla, pageRequest).getContent().get(0).getValor();
    }

    public BigDecimal valorAtual(Long acaoId) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "dataHora");
        return historicoValorRepository.findByAcaoId(acaoId, pageRequest).getContent().get(0).getValor();
    }

    public BigDecimal valorAtual(Acao acao) {
        return valorAtual(acao.getId());
    }
}
