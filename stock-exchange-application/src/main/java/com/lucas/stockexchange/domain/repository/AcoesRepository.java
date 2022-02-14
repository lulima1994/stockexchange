package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Acao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcoesRepository extends JpaRepository<Acao, Long> {

}
