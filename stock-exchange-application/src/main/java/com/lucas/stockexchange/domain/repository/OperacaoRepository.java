package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Operacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
    @Query("select o from Operacao o where usuario.login=:login")
    Page<Operacao> findCurrentByLogin(String login, Pageable pageable);
}
