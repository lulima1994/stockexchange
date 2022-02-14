package com.lucas.stockexchange.rest;

import com.lucas.stockexchange.dto.acao.AcaoRequest;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import com.lucas.stockexchange.service.AcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AcaoController {
    private final AcaoService acaoService;

    @PostMapping("/acao")
    public ResponseEntity<AcaoResponse> adicionar(@RequestBody AcaoRequest acaoRequest) {
        if (acaoRequest.getId() != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        AcaoResponse acaoResponse = acaoService.salvar(acaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(acaoResponse);
    }

    @PutMapping("/acao")
    public ResponseEntity<AcaoResponse> atualizar(@RequestBody AcaoRequest acaoRequest) {
        if (acaoRequest.getId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        AcaoResponse acaoResponse = acaoService.salvar(acaoRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(acaoResponse);
    }

    @GetMapping("/acao/{id}")
    public ResponseEntity<AcaoResponse> buscarPorId(@PathVariable Long id) {
        AcaoResponse acaoResponse = acaoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(acaoResponse);
    }

    @GetMapping("/acao")
    public ResponseEntity<Page<AcaoResponse>> buscarPorPagina(Pageable pageable) {
        Page<AcaoResponse> acaoResponses = acaoService.buscarPorPagina(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(acaoResponses);
    }

    @DeleteMapping("/acao/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        acaoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
