package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.*;
import com.lucas.stockexchange.domain.repository.CarteiraRepository;
import com.lucas.stockexchange.domain.repository.OperacaoRepository;
import com.lucas.stockexchange.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExecutarMovimentacaoAcao {
    private final PedidoRepository pedidoRepository;
    private final CarteiraRepository carteiraRepository;
    private final ValorAcaoService valorAcaoService;
    private final OperacaoRepository operacaoRepository;
    private final AjustarValorAcao ajustarValorAcao;

    @Transactional
    public void executarPedidos() {
        executarCompras();
        executarVendas();
    }

    @Transactional
    public void executarCompras() {
        List<Pedido> pedidos = pedidoRepository.buscarPedidosAbertosComprasExecutaveis();
        for (Pedido cadaPedido : pedidos) {
            registrarCarteira(cadaPedido);
            registrarOperacao(cadaPedido, TipoOperacao.CREDITO);
            atualizarStatus(cadaPedido);
            ajustarValorAcao.adicionarPedidos(cadaPedido);
        }
    }

    @Transactional
    public void executarVendas() {
        List<Pedido> pedidos = pedidoRepository.buscarPedidosAbertosVendasExecutaveis();
        for (Pedido cadaPedido : pedidos) {
            registrarCarteira(cadaPedido);
            registrarOperacao(cadaPedido, TipoOperacao.DEBITO);
            atualizarStatus(cadaPedido);
            ajustarValorAcao.adicionarPedidos(cadaPedido);
        }
    }

    private void registrarCarteira(Pedido pedido) {
        Usuario usuario = pedido.getUsuario();
        Acao acao = pedido.getAcao();
        Optional<Carteira> optionalCarteira = carteiraRepository.verifyCarteira(usuario.getId(), acao.getId());
        Carteira carteira;
        if (optionalCarteira.isPresent()) {
            carteira = optionalCarteira.get();
            if (pedido.getTipo() == (TipoTransacao.COMPRA)) {
                carteira.setQuantidade(carteira.getQuantidade() + pedido.getQuantidade());
            } else if (carteira.getQuantidade() - pedido.getQuantidade() >= 0) {
                carteira.setQuantidade(carteira.getQuantidade() - pedido.getQuantidade());
            } else {
                throw new RuntimeException("venda nao disponivel");
            }
        } else {
            carteira = new Carteira();
            carteira.setUsuario(usuario);
            carteira.setAcao(acao);
            if (pedido.getTipo() == (TipoTransacao.COMPRA)) {
                carteira.setQuantidade(pedido.getQuantidade());
            } else {
                throw new RuntimeException("venda nao disponivel");
            }
        }
        carteiraRepository.save(carteira);
    }

    private Operacao registrarOperacao(Pedido pedido, TipoOperacao tipoOperacao) {
        Operacao operacao = new Operacao();
        operacao.setDataHora(LocalDateTime.now());
        operacao.setQuantidade(pedido.getQuantidade());
        operacao.setUsuario(pedido.getUsuario());
        operacao.setAcao(pedido.getAcao());
        operacao.setTipo(tipoOperacao);
        operacao.setValor(valorAcaoService.valorAtual(pedido.getAcao()));
        operacaoRepository.save(operacao);
        return operacao;
    }

    private void atualizarStatus(Pedido pedido) {
        pedido.setStatus(StatusPedido.EXECUTADO);
        pedidoRepository.save(pedido);
    }
}
