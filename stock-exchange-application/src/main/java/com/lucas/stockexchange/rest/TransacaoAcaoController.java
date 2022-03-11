package com.lucas.stockexchange.rest;

import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoResponse;
import com.lucas.stockexchange.service.TransacaoAcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransacaoAcaoController {
    private final TransacaoAcaoService transacaoAcaoService;

    @PostMapping("/comprar")
    public ResponseEntity<TransacaoAcaoResponse> comprar(@RequestBody TransacaoAcaoRequest transacaoAcaoRequest) {
        TransacaoAcaoResponse transacaoAcaoResponse = transacaoAcaoService.comprar(transacaoAcaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoAcaoResponse);
    }

    @PostMapping("/vender")
    public ResponseEntity<TransacaoAcaoResponse> vender(@RequestBody TransacaoAcaoRequest transacaoAcaoRequest) {
        TransacaoAcaoResponse transacaoAcaoResponse = transacaoAcaoService.vender(transacaoAcaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoAcaoResponse);
    }
}
