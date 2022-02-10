package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.HistoricoValores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<HistoricoValores, Long> {

}
