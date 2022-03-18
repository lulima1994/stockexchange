package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.HistoricoValor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoricoValorRepository extends JpaRepository<HistoricoValor, Long> {
    @Query("select hv from HistoricoValor hv where acao.sigla=:sigla")
    Page<HistoricoValor> findBySigla(String sigla, Pageable pageable);

    @Query("select hv from HistoricoValor hv where acao.id=:id")
    Page<HistoricoValor> findByAcaoId(Long id, Pageable pageable);
}
