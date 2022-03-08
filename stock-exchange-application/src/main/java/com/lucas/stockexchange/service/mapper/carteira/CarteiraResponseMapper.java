package com.lucas.stockexchange.service.mapper.carteira;

import com.lucas.stockexchange.domain.model.Carteira;
import com.lucas.stockexchange.dto.carteira.CarteiraResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarteiraResponseMapper {

    public CarteiraResponse mapear(Carteira carteira) {
        CarteiraResponse carteiraResponse = new CarteiraResponse();
        carteiraResponse.setId(carteira.getId());
        carteiraResponse.setQuantidade(carteira.getQuantidade());
        return carteiraResponse;
    }
}
