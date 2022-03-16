package com.lucas.stockexchange.service.mapper.acao;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.repository.HistoricoValorRepository;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import com.lucas.stockexchange.service.ValorAcaoService;
import com.lucas.stockexchange.service.mapper.setor.SetorResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcaoResponseMapper {
    private final ValorAcaoService valorAcaoService;
    private final SetorResponseMapper setorResponseMapper;

    public AcaoResponse mapear(Acao acao) {
        AcaoResponse acaoResponse = new AcaoResponse();
        acaoResponse.setId(acao.getId());
        acaoResponse.setNome(acao.getNome());
        acaoResponse.setSigla(acao.getSigla());
        acaoResponse.setValor(valorAcaoService.valorAtual(acao.getSigla()));
        acaoResponse.setQuantidade(acao.getQuantidade());
        acaoResponse.setTipo(acao.getTipo());
        acaoResponse.setSetorResponse(setorResponseMapper.mapear(acao.getSetor()));
        return acaoResponse;
    }
}
