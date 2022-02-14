package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.model.Setor;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.SetorRepository;
import com.lucas.stockexchange.dto.acao.AcaoRequest;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import com.lucas.stockexchange.dto.setor.SetorRequest;
import com.lucas.stockexchange.dto.setor.SetorResponse;
import com.lucas.stockexchange.service.mapper.acao.AcaoMapper;
import com.lucas.stockexchange.service.mapper.acao.AcaoResponseMapper;
import com.lucas.stockexchange.service.mapper.setor.SetorMapper;
import com.lucas.stockexchange.service.mapper.setor.SetorResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SetorService {
    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;
    private final SetorResponseMapper setorResponseMapper;

    public SetorResponse salvar(SetorRequest setorRequest) {
        Setor setor = setorMapper.mapear(setorRequest);
        setorRepository.save(setor);
        SetorResponse setorResponse = setorResponseMapper.mapear(setor);
        return setorResponse;
    }

    public SetorResponse buscarPorId(Long id) {
        Optional<Setor> setorOptional = setorRepository.findById(id);
        if (setorOptional.isEmpty())
            throw new RuntimeException("setor " + id + " nao encontrado");
        SetorResponse setorResponse = setorResponseMapper.mapear(setorOptional.get());
        return setorResponse;
    }

    public Page<SetorResponse> buscarPorPagina(Pageable pageable) {
        Page<Setor> setores = setorRepository.findAll(pageable);
        Page<SetorResponse> setorResponses = setores.map(setorResponseMapper::mapear);
        return setorResponses;
    }

    public void deletarPorId(Long id) {
        setorRepository.deleteById(id);
    }
}
