package com.lucas.stockexchange.rest;

import com.lucas.stockexchange.dto.operacao.OperacaoResponse;
import com.lucas.stockexchange.service.OperacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OperacaoController {
    private final OperacaoService operacaoService;

    @GetMapping("/operacao")
    public ResponseEntity<Page<OperacaoResponse>> buscarOperacao(String login,
    @PageableDefault(sort = {"dataHora"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OperacaoResponse> operacaoResponses = operacaoService.buscarPorLogin(login, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(operacaoResponses);
    }
}
