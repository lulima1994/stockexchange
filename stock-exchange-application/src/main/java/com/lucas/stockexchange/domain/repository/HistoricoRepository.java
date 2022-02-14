package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.HistoricoValor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<HistoricoValor, Long> {

}
