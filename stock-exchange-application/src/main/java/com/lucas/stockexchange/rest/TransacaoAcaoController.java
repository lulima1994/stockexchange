package com.lucas.stockexchange.rest;

import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import com.lucas.stockexchange.service.TransacaoAcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransacaoAcaoController {
    private final TransacaoAcaoService transacaoAcaoService;

    @PostMapping("/comprar")
    public ResponseEntity<?> comprar(@RequestBody TransacaoAcaoRequest transacaoAcaoRequest) {
        transacaoAcaoService.comprar(transacaoAcaoRequest);
        return null;
    }

    @PostMapping("/vender")
    public ResponseEntity<?> vender(@RequestBody TransacaoAcaoRequest transacaoAcaoRequest) {
        transacaoAcaoService.vender(transacaoAcaoRequest);
        return null;
    }
}
