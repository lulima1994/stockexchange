package com.lucas.stockexchange.rest;

import com.lucas.stockexchange.dto.setor.SetorRequest;
import com.lucas.stockexchange.dto.setor.SetorResponse;
import com.lucas.stockexchange.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SetorController {
    private final SetorService setorService;

    @PostMapping("/setor")
    public ResponseEntity<SetorResponse> adicionar(@RequestBody SetorRequest setorRequest) {
        if (setorRequest.getId() != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        SetorResponse setorResponse = setorService.salvar(setorRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(setorResponse);
    }

    @PutMapping("/setor")
    public ResponseEntity<SetorResponse> atualizar(@RequestBody SetorRequest setorRequest) {
        if (setorRequest.getId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        SetorResponse setorResponse = setorService.salvar(setorRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(setorResponse);
    }

    @GetMapping("/setor/{id}")
    public ResponseEntity<SetorResponse> buscarPorId(@PathVariable Long id) {
        SetorResponse setorResponse = setorService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(setorResponse);
    }

    @GetMapping("/setor")
    public ResponseEntity<Page<SetorResponse>> buscarPorPagina(Pageable pageable) {
        Page<SetorResponse> setorResponses = setorService.buscarPorPagina(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(setorResponses);
    }

    @DeleteMapping("/setor/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        setorService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
