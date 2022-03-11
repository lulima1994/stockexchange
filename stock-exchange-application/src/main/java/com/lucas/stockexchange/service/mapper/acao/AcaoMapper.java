package com.lucas.stockexchange.service.mapper.acao;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.repository.SetorRepository;
import com.lucas.stockexchange.dto.acao.AcaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcaoMapper {
    private final SetorRepository setorRepository;

    public Acao mapear(AcaoRequest acaoRequest) {
        Acao acao = new Acao();
        acao.setId(acaoRequest.getId());
        acao.setNome(acaoRequest.getNome());
        acao.setSigla(acaoRequest.getSigla());
        acao.setValor(acaoRequest.getValor());
        acao.setQuantidade(acaoRequest.getQuantidade());
        acao.setTipo(acaoRequest.getTipo());
        acao.setSetor(setorRepository.findById(acaoRequest.getSetorId()).orElseThrow());
        return acao;
    }
}
