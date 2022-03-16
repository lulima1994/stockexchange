package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Carteira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    @Query("select c from Carteira c where c.usuario.id=:usuarioId and c.acao.id=:acaoId")
    Optional<Carteira> verifyCarteira(Long usuarioId, Long acaoId);

    @Query("select c from Carteira c where c.usuario.cpf=:cpf and c.quantidade>0")
    Page<Carteira> findByCpf(String cpf, Pageable pageable);
}
