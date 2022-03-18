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

    @Transactional
    public void executarCompras() {
        List<Pedido> pedidos = pedidoRepository.buscarPedidosAbertosComprasExecutaveis();
        for (int i = 0; i < pedidos.size(); i++) { //for(Pedido cadaPedido : pedidos)
            Pedido cadaPedido = pedidos.get(i);
            Usuario usuario = cadaPedido.getUsuario();
            Acao acao = cadaPedido.getAcao();
            Optional<Carteira> optionalCarteira = carteiraRepository.verifyCarteira(usuario.getId(), acao.getId());
            Carteira carteira;
            if (optionalCarteira.isPresent()) {
                carteira = optionalCarteira.get();
                carteira.setQuantidade(carteira.getQuantidade() + cadaPedido.getQuantidade());
            } else {
                carteira = new Carteira();
                carteira.setUsuario(usuario);
                carteira.setAcao(acao);
                carteira.setQuantidade(cadaPedido.getQuantidade());
            }
            carteiraRepository.save(carteira);
            registrarOperacao(cadaPedido.getQuantidade(), usuario, acao, TipoOperacao.CREDITO);
            cadaPedido.setStatus(StatusPedido.EXECUTADO);
            pedidoRepository.save(cadaPedido);
        }
    }

    public void executarVendas() {

    }

    private Operacao registrarOperacao(Integer quantidade, Usuario usuario, Acao acao, TipoOperacao tipoOperacao) {
        Operacao operacao = new Operacao();
        operacao.setDataHora(LocalDateTime.now());
        operacao.setQuantidade(quantidade);
        operacao.setUsuario(usuario);
        operacao.setAcao(acao);
        operacao.setTipo(tipoOperacao);
        operacao.setValor(valorAcaoService.valorAtual(acao));
        operacaoRepository.save(operacao);
        return operacao;
    }
}
