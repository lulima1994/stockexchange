package com.lucas.stockexchange.service.mapper.carteira;

import com.lucas.stockexchange.domain.model.Carteira;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.UsuarioRepository;
import com.lucas.stockexchange.dto.carteira.CarteiraRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarteiraMapper {
    private final AcaoRepository acaoRepository;
    private final UsuarioRepository usuarioRepository;

    public Carteira mapear(CarteiraRequest carteiraRequest) {
        Carteira carteira = new Carteira();
        carteira.setId(carteiraRequest.getId());
        carteira.setQuantidade(carteiraRequest.getQuantidade());
        carteira.setAcao(acaoRepository.findById(carteiraRequest.getAcaoId()).orElseThrow());
        carteira.setUsuario(usuarioRepository.findById(carteiraRequest.getUsuarioId()).orElseThrow());
        return carteira;
    }
}
