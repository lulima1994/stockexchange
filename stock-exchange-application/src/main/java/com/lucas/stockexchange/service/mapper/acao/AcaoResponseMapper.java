package com.lucas.stockexchange.service.mapper.acao;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.repository.HistoricoValorRepository;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcaoResponseMapper {
    private final HistoricoValorRepository historicoValorRepository;

    public AcaoResponse mapear(Acao acao) {
        PageRequest pageRequest = PageRequest.of(0,1, Sort.Direction.DESC,"dataHora");
        AcaoResponse acaoResponse = new AcaoResponse();
        acaoResponse.setId(acao.getId());
        acaoResponse.setNome(acao.getNome());
        acaoResponse.setSigla(acao.getSigla());
        acaoResponse.setTipo(acao.getTipo());
        acaoResponse.setDescricao(acao.getDescricao());
        acaoResponse.setQuantidade(acao.getQuantidade());
        acaoResponse.setValor(historicoValorRepository.findCurrentByInitial(acao.getSigla(), pageRequest)
                 .getContent().get(0).getValor());
        return acaoResponse;
    }
}
