package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query(value = "SELECT * FROM pedido p\n" +
            "WHERE p.status = \"ABERTO\" AND p.tipo = \"COMPRA\" AND (p.valor IS NULL OR p.valor >= (SELECT hv.valor FROM historico_valor hv WHERE hv.acao_id = p.acao_id ORDER BY hv.data_hora DESC LIMIT 1))", nativeQuery = true)
    List<Pedido> buscarPedidosAbertosComprasExecutaveis();

    @Query(value = "SELECT * FROM pedido p\n" +
            "WHERE p.status = \"ABERTO\" AND p.tipo = \"VENDA\" AND (p.valor IS NULL OR p.valor <= (SELECT hv.valor FROM historico_valor hv WHERE hv.acao_id = p.acao_id ORDER BY hv.data_hora DESC LIMIT 1))", nativeQuery = true)
    List<Pedido> buscarPedidosAbertosVendasExecutaveis();
}
