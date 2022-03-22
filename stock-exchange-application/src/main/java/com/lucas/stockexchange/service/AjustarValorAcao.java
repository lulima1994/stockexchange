package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.*;
import com.lucas.stockexchange.domain.repository.HistoricoValorRepository;
import com.lucas.stockexchange.domain.repository.PedidoExecutadoTempRepository;
import com.lucas.stockexchange.dto.PedidoExecutadoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AjustarValorAcao {
    private final PedidoExecutadoTempRepository pedidoExecutadoTempRepository;
    private final ValorAcaoService valorAcaoService;
    private final HistoricoValorRepository historicoValorRepository;

    public void adicionarPedidos(Pedido pedido) {
        if (pedido.getStatus() != StatusPedido.EXECUTADO) throw new RuntimeException("error");
        PedidoExecutadoTemp pedidoExecutadoTemp = new PedidoExecutadoTemp();
        pedidoExecutadoTemp.setDataHoraRegistro(pedido.getDataHora());
        pedidoExecutadoTemp.setQuantidade(pedido.getQuantidade());
        pedidoExecutadoTemp.setTipo(pedido.getTipo());
        pedidoExecutadoTemp.setPedido(pedido);
        pedidoExecutadoTempRepository.save(pedidoExecutadoTemp);
    }

    @Scheduled(cron = "0/30 * * * * *")
    @Transactional
    public void executarAjustes() {
        log.info("Executando ajustes de valores");
        // Busca todos os pedidos que ainda nao liquidaram o valor da acao no banco
        List<PedidoExecutadoTemp> todosPedidos = pedidoExecutadoTempRepository.findAll();

        // Agrupa por acao o somatorio de valores dos pedidos que nao foram liquidados. O hashmap contem <ID da acao, Valor agrupado>
        HashMap<Long, PedidoExecutadoDTO> pedidosAgrupados = new HashMap<>();
        for (PedidoExecutadoTemp cadaPedido : todosPedidos) {
            PedidoExecutadoDTO pedidoAgrupado = pedidosAgrupados.getOrDefault(cadaPedido.getPedido().getAcao().getId(), new PedidoExecutadoDTO(cadaPedido.getPedido().getAcao()));
            if (cadaPedido.getTipo() == TipoTransacao.COMPRA) {
                pedidoAgrupado.setQuantidade(pedidoAgrupado.getQuantidade() + cadaPedido.getQuantidade());
                pedidoAgrupado.setValorAcumulado(pedidoAgrupado.getValorAcumulado().add(cadaPedido.getPedido().getValor().multiply(BigDecimal.valueOf(cadaPedido.getQuantidade()))));
            } else {
                pedidoAgrupado.setQuantidade(pedidoAgrupado.getQuantidade() - cadaPedido.getQuantidade());
                pedidoAgrupado.setValorAcumulado(pedidoAgrupado.getValorAcumulado().subtract(cadaPedido.getPedido().getValor().multiply(BigDecimal.valueOf(cadaPedido.getQuantidade()))));
            }
            pedidosAgrupados.put(cadaPedido.getPedido().getAcao().getId(), pedidoAgrupado);
        }

        // Depois de agrupado, busca o valor atual da acao, calcula a diferenca baseada nos pedidos e aplica o valor num novo historico valor
        for (var cadaAcao : pedidosAgrupados.entrySet()) {
            Long acaoID = cadaAcao.getKey();
            BigDecimal valorAtual = valorAcaoService.valorAtual(acaoID);
            BigDecimal valorAjustado = valorAtual.add(cadaAcao.getValue().getValorAcumulado().multiply(BigDecimal.valueOf(0.01)));
            HistoricoValor historicoValor = new HistoricoValor();
            historicoValor.setValor(valorAjustado);
            historicoValor.setDataHora(LocalDateTime.now());
            historicoValor.setAcao(cadaAcao.getValue().getAcao());
            historicoValorRepository.save(historicoValor);

            log.info("Acao {} ajustada de R${} para R${}", historicoValor.getAcao().getNome(), valorAtual, valorAjustado);
        }

        // Exclui todos os pedidos que acabaram de liquidar o valor da acao, para nao executarem novamente
        pedidoExecutadoTempRepository.deleteAll(todosPedidos);
        log.info("Ajustes de valores executados com sucesso");
    }
}
