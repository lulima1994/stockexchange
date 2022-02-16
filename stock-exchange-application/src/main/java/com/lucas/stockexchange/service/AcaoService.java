package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.model.HistoricoValor;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.HistoricoValorRepository;
import com.lucas.stockexchange.dto.acao.AcaoRequest;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import com.lucas.stockexchange.service.mapper.acao.AcaoMapper;
import com.lucas.stockexchange.service.mapper.acao.AcaoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AcaoService {
    private final AcaoRepository acaoRepository;
    private final AcaoMapper acaoMapper;
    private final AcaoResponseMapper acaoResponseMapper;
    private final HistoricoValorRepository historicoValorRepository;

    @Transactional
    public AcaoResponse salvar(AcaoRequest acaoRequest) {
        Acao acao = acaoMapper.mapear(acaoRequest);
        acaoRepository.save(acao);
        HistoricoValor historicoValor = new HistoricoValor();
        historicoValor.setValor(acaoRequest.getValor().divide(BigDecimal.valueOf(acaoRequest.getQuantidade()), 2, RoundingMode.HALF_UP));
        historicoValor.setDataHora(LocalDateTime.now());
        historicoValor.setAcao(acao);
        historicoValorRepository.save(historicoValor);
        AcaoResponse acaoResponse = acaoResponseMapper.mapear(acao);
        acaoResponse.setValor(historicoValor.getValor());
        return acaoResponse;
    }

    public AcaoResponse buscarPorId(Long id) {
        Optional<Acao> acaoOptional = acaoRepository.findById(id);
        if (acaoOptional.isEmpty())
            throw new RuntimeException("acao " + id + " nao encontrada");
        return acaoResponseMapper.mapear(acaoOptional.get());
    }

    public Page<AcaoResponse> buscarPorPagina(Pageable pageable) {
        Page<Acao> acaos = acaoRepository.findAll(pageable);
        return acaos.map(acaoResponseMapper::mapear);
    }

    public void deletarPorId(Long id) {
        acaoRepository.deleteById(id);
    }
}
