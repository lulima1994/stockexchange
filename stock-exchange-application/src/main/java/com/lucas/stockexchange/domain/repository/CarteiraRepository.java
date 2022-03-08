package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    @Query("select c from Carteira c where c.quantidade=:quantidade")
    Optional<Carteira> findByQtd(Integer quantidade);

    @Query("select c from Carteira c where c.usuario.id=:usuarioId and c.acao.id=:acaoId")
    Optional<Carteira> verifyWallet(Long usuarioId, Long acaoId);
}
