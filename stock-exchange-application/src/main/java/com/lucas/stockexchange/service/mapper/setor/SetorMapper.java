package com.lucas.stockexchange.service.mapper.setor;

import com.lucas.stockexchange.domain.model.Setor;
import com.lucas.stockexchange.dto.setor.SetorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetorMapper {
    public Setor mapear(SetorRequest setorRequest) {
        Setor setor = new Setor();
        setor.setId(setorRequest.getId());
        setor.setNome(setorRequest.getNome());
        setor.setDescricao(setorRequest.getDescricao());
        return setor;
    }
}
