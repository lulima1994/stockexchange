package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

}
