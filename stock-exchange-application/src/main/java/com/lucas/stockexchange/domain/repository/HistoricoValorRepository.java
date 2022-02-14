package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.HistoricoValor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HistoricoValorRepository extends JpaRepository<HistoricoValor, Long> {
//    @Query("select hv from HistoricoValor hv where acao.sigla=:sigla order by dataHora desc limit 1")
//    Optional<HistoricoValor> findCurrentByInitial(String sigla);
}
