package com.lucas.stockexchange.service.mapper.setor;

import com.lucas.stockexchange.domain.model.Setor;
import com.lucas.stockexchange.dto.setor.SetorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetorResponseMapper {
    public SetorResponse mapear(Setor setor) {
        SetorResponse setorResponse = new SetorResponse();
        setorResponse.setId(setor.getId());
        setorResponse.setNome(setor.getNome());
        setorResponse.setDescricao(setor.getDescricao());
        return setorResponse;
    }
}
