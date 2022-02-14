package com.lucas.stockexchange.service.mapper.setor;

import com.lucas.stockexchange.domain.model.Setor;
import com.lucas.stockexchange.dto.setor.SetorRequest;
import org.springframework.stereotype.Component;

@Component
public class SetorMapper {
    public Setor mapear(SetorRequest setorRequest) {
        Setor setor = new Setor();
        setor.setNome(setorRequest.getNome());
        setor.setDescricao(setorRequest.getDescricao());
        return setor;
    }
}
