package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Acao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AcaoRepository extends JpaRepository<Acao, Long> {
    @Query("select a from Acao a where a.sigla=:sigla")
    Optional<Acao> findBySigla(String sigla);
}
