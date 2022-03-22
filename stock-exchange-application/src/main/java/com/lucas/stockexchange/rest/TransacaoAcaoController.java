package com.lucas.stockexchange.rest;

import com.lucas.stockexchange.dto.carteira.CarteiraResponse;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoResponse;
import com.lucas.stockexchange.service.TransacaoAcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TransacaoAcaoController {
    private final TransacaoAcaoService transacaoAcaoService;

    @PostMapping("/comprar")
    public ResponseEntity<TransacaoAcaoResponse> comprarAcao(@RequestBody TransacaoAcaoRequest transacaoAcaoRequest) {
        TransacaoAcaoResponse transacaoAcaoResponse = transacaoAcaoService.registrarCompra(transacaoAcaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoAcaoResponse);
    }

    @PostMapping("/vender")
    public ResponseEntity<TransacaoAcaoResponse> venderAcao(@RequestBody TransacaoAcaoRequest transacaoAcaoRequest) {
        TransacaoAcaoResponse transacaoAcaoResponse = transacaoAcaoService.registrarVenda(transacaoAcaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoAcaoResponse);
    }

    @GetMapping("/carteira")
    public ResponseEntity<Page<CarteiraResponse>> buscarCarteira(String cpf, @PageableDefault(sort = {"acao.sigla"}) Pageable pageable) {
        Page<CarteiraResponse> carteiraResponses = transacaoAcaoService.buscarPorCpf(cpf, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(carteiraResponses);
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        transacaoAcaoService.cancelarPedido(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
