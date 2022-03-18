package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.*;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.CarteiraRepository;
import com.lucas.stockexchange.domain.repository.PedidoRepository;
import com.lucas.stockexchange.domain.repository.UsuarioRepository;
import com.lucas.stockexchange.dto.carteira.CarteiraResponse;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransacaoAcaoService {
    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;
    private final AcaoRepository acaoRepository;
    private final PedidoRepository pedidoRepository;
    private final ValorAcaoService valorAcaoService;
    private final ExecutarMovimentacaoAcao executarMovimentacaoAcao;

    public TransacaoAcaoResponse registrarCompra(TransacaoAcaoRequest transacaoAcaoRequest) {
        validarQuantidadeValorPrazo(transacaoAcaoRequest);
        Usuario usuario = validarUsuario(transacaoAcaoRequest);
        Acao acao = validarAcao(transacaoAcaoRequest);
        Pedido pedido = registrarPedido(usuario, acao, transacaoAcaoRequest, TipoTransacao.COMPRA);
        executarMovimentacaoAcao.executarPedidos();
        return new TransacaoAcaoResponse(pedido.getId());
    }

    public TransacaoAcaoResponse registrarVenda(TransacaoAcaoRequest transacaoAcaoRequest) {
        validarQuantidadeValorPrazo(transacaoAcaoRequest);
        Usuario usuario = validarUsuario(transacaoAcaoRequest);
        Acao acao = validarAcao(transacaoAcaoRequest);
        Pedido pedido = registrarPedido(usuario, acao, transacaoAcaoRequest, TipoTransacao.VENDA);
        executarMovimentacaoAcao.executarPedidos();
        return new TransacaoAcaoResponse(pedido.getId());
    }

    public Page<CarteiraResponse> buscarPorCpf(String cpf, Pageable pageable) {
        Page<Carteira> carteiras = carteiraRepository.findByCpf(cpf, pageable);
        return carteiras.map((cadaCarteira) -> {
            CarteiraResponse carteiraResponse = new CarteiraResponse();
            carteiraResponse.setSigla(cadaCarteira.getAcao().getSigla());
            carteiraResponse.setQuantidade(cadaCarteira.getQuantidade());
            carteiraResponse.setValorAtual(valorAcaoService.valorAtual(cadaCarteira.getAcao().getSigla()));
            //carteiraResponse.setCustoMedio();
            return carteiraResponse;
        });
    }

    private Pedido registrarPedido(Usuario usuario, Acao acao, TransacaoAcaoRequest transacaoAcaoRequest, TipoTransacao tipoTransacao) {
        Pedido pedido = new Pedido();
        pedido.setValor(transacaoAcaoRequest.getValor());
        pedido.setQuantidade(transacaoAcaoRequest.getQuantidade());
        pedido.setDataHora(LocalDateTime.now());
        pedido.setPrazo(transacaoAcaoRequest.getPrazo());
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setTipo(tipoTransacao);
        pedido.setUsuario(usuario);
        pedido.setAcao(acao);
        return pedidoRepository.save(pedido);
    }

    private void validarQuantidadeValorPrazo(TransacaoAcaoRequest transacaoAcaoRequest) {
        if (transacaoAcaoRequest.getQuantidade() <= 0) {
            throw new RuntimeException("quantidade irregular");
        }
        if (transacaoAcaoRequest.getValor() != null && transacaoAcaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("valor irregular");
        }
        if (transacaoAcaoRequest.getPrazo() == null) {
            transacaoAcaoRequest.setPrazo(LocalDateTime.now().plusDays(1));
        }
    }

    private Usuario validarUsuario(TransacaoAcaoRequest transacaoAcaoRequest) {
        Optional<Usuario> login = usuarioRepository.findByLogin(transacaoAcaoRequest.getLogin());
        if (login.isEmpty()) {
            throw new RuntimeException("usuario " + transacaoAcaoRequest.getLogin() + " nao encontrado");
        }
        return login.get();
    }

    private Acao validarAcao(TransacaoAcaoRequest transacaoAcaoRequest) {
        Optional<Acao> sigla = acaoRepository.findBySigla(transacaoAcaoRequest.getSigla());
        if (sigla.isEmpty()) {
            throw new RuntimeException("acao " + transacaoAcaoRequest.getSigla() + " nao encontrada");
        }
        return sigla.get();
    }
}