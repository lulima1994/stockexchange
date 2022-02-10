package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Acoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcoesRepository extends JpaRepository<Acoes, Long> {

}
