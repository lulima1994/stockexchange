package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.dto.acao.AcaoRequest;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import com.lucas.stockexchange.service.mapper.acao.AcaoMapper;
import com.lucas.stockexchange.service.mapper.acao.AcaoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AcaoService {
    private final AcaoRepository acaoRepository;
    private final AcaoMapper acaoMapper;
    private final AcaoResponseMapper acaoResponseMapper;

    public AcaoResponse salvar(AcaoRequest acaoRequest) {
        Acao acao = acaoMapper.mapear(acaoRequest);
        acaoRepository.save(acao);
        AcaoResponse acaoResponse = acaoResponseMapper.mapear(acao);
        return acaoResponse;
    }

    public AcaoResponse buscarPorId(Long id) {
        Optional<Acao> acaoOptional = acaoRepository.findById(id);
        if (acaoOptional.isEmpty())
            throw new RuntimeException("acao " + id + " nao encontrada");
        AcaoResponse acaoResponse = acaoResponseMapper.mapear(acaoOptional.get());
        return acaoResponse;
    }

    public Page<AcaoResponse> buscarPorPagina(Pageable pageable) {
        Page<Acao> acaos = acaoRepository.findAll(pageable);
        Page<AcaoResponse> acaoResponses = acaos.map(acaoResponseMapper::mapear);
        return acaoResponses;
    }

    public void deletarPorId(Long id) {
        acaoRepository.deleteById(id);
    }
}
