package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.*;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.CarteiraRepository;
import com.lucas.stockexchange.domain.repository.OperacaoRepository;
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
    private final OperacaoRepository operacaoRepository;
    private final ValorAcaoService valorAcaoService;

    public TransacaoAcaoResponse comprar(TransacaoAcaoRequest transacaoAcaoRequest) {
        if (transacaoAcaoRequest.getQuantidade() <= 0) {
            throw new RuntimeException("quantidade de compra irregular");
        }
        Optional<Usuario> login = usuarioRepository.findByLogin(transacaoAcaoRequest.getLogin());
        if (login.isEmpty()) {
            throw new RuntimeException("usuario " + transacaoAcaoRequest.getLogin() + " nao encontrado");
        }
        Optional<Acao> sigla = acaoRepository.findBySigla(transacaoAcaoRequest.getSigla());
        if (sigla.isEmpty()) {
            throw new RuntimeException("acao " + transacaoAcaoRequest.getSigla() + " nao encontrada");
        }
        Usuario usuario = login.get();
        Acao acao = sigla.get();
        Carteira carteira;
        Optional<Carteira> optionalCarteira = carteiraRepository.verifyCarteira(usuario.getId(), acao.getId());
        if (optionalCarteira.isPresent()) {
            carteira = optionalCarteira.get();
            carteira.setQuantidade(carteira.getQuantidade() + transacaoAcaoRequest.getQuantidade());
            if (acao.getQuantidade() - transacaoAcaoRequest.getQuantidade() < 0) {
                throw new RuntimeException("quantidade de acao " + transacaoAcaoRequest.getSigla() + " nao disponivel");
            } else {
                acao.setQuantidade(acao.getQuantidade() - transacaoAcaoRequest.getQuantidade());
            }
        } else {
            carteira = new Carteira();
            carteira.setUsuario(usuario);
            carteira.setAcao(acao);
            carteira.setQuantidade(transacaoAcaoRequest.getQuantidade());
            acao.setQuantidade(acao.getQuantidade() - transacaoAcaoRequest.getQuantidade());
        }
        carteiraRepository.save(carteira);

        Operacao operacao = registrarOperacao(transacaoAcaoRequest.getQuantidade(), usuario, acao, TipoOperacao.CREDITO);

        return mapear(operacao, carteira);
    }

    public TransacaoAcaoResponse vender(TransacaoAcaoRequest transacaoAcaoRequest) {
        if (transacaoAcaoRequest.getQuantidade() <= 0) {
            throw new RuntimeException("quantidade de venda irregular");
        }
        Optional<Usuario> login = usuarioRepository.findByLogin(transacaoAcaoRequest.getLogin());
        if (login.isEmpty()) {
            throw new RuntimeException("usuario " + transacaoAcaoRequest.getLogin() + " nao encontrado");
        }
        Optional<Acao> sigla = acaoRepository.findBySigla(transacaoAcaoRequest.getSigla());
        if (sigla.isEmpty()) {
            throw new RuntimeException("acao " + transacaoAcaoRequest.getSigla() + " nao encontrada");
        }
        Usuario usuario = login.get();
        Acao acao = sigla.get();
        Carteira carteira;
        Optional<Carteira> optionalCarteira = carteiraRepository.verifyCarteira(usuario.getId(), acao.getId());
        if (optionalCarteira.isPresent()) {
            carteira = optionalCarteira.get();
            if (transacaoAcaoRequest.getQuantidade() > carteira.getQuantidade()) {
                throw new RuntimeException("quantidade " + transacaoAcaoRequest.getQuantidade() + " nao disponivel");
            } else {
                carteira.setQuantidade(carteira.getQuantidade() - transacaoAcaoRequest.getQuantidade());
            }
        } else {
            throw new RuntimeException("carteira nao encontrada");
        }
        acao.setQuantidade(acao.getQuantidade() + transacaoAcaoRequest.getQuantidade());
        carteiraRepository.save(carteira);

        Operacao operacao = registrarOperacao(transacaoAcaoRequest.getQuantidade(), usuario, acao, TipoOperacao.DEBITO);

        return mapear(operacao, carteira);
    }

    private Operacao registrarOperacao(Integer quantidade, Usuario usuario, Acao acao, TipoOperacao tipoOperacao) {
        Operacao operacao = new Operacao();
        operacao.setDataHora(LocalDateTime.now());
        operacao.setQuantidade(quantidade);
        operacao.setUsuario(usuario);
        operacao.setAcao(acao);
        operacao.setTipo(tipoOperacao);
        operacao.setValor(valorAcaoService.valorAtual(acao.getSigla()));
        operacaoRepository.save(operacao);
        return operacao;
    }

    private TransacaoAcaoResponse mapear(Operacao operacao, Carteira carteira) {
        TransacaoAcaoResponse transacaoAcaoResponse = new TransacaoAcaoResponse();
        transacaoAcaoResponse.setDataHora(operacao.getDataHora());
        transacaoAcaoResponse.setValorTotal(operacao.getValor().multiply(BigDecimal.valueOf(operacao.getQuantidade())));
        transacaoAcaoResponse.setValorUnitario(operacao.getValor());
        transacaoAcaoResponse.setQuantidadeTransacao(operacao.getQuantidade());
        transacaoAcaoResponse.setQuantidadeCarteira(carteira.getQuantidade());
        transacaoAcaoResponse.setTipo(operacao.getTipo());
        return transacaoAcaoResponse;
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
}
