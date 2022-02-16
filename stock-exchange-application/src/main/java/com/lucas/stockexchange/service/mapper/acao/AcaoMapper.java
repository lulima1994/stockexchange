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
        acao.setNome(acaoRequest.getNome());
        acao.setSigla(acaoRequest.getSigla());
        acao.setTipo(acaoRequest.getTipo());
        acao.setDescricao(acaoRequest.getDescricao());
        acao.setQuantidade(acaoRequest.getQuantidade());
        acao.setSetor(setorRepository.findById(acaoRequest.getSetorId()).orElseThrow());
        return acao;
    }
}
