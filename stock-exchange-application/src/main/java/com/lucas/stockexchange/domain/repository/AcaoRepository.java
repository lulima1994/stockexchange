package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Acao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcaoRepository extends JpaRepository<Acao, Long> {
}
