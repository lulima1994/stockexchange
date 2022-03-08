package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.*;
import com.lucas.stockexchange.domain.repository.*;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransacaoAcaoService {
    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;
    private final AcaoRepository acaoRepository;
    private final HistoricoValorRepository historicoValorRepository;
    private final OperacaoRepository operacaoRepository;

    public void comprar(TransacaoAcaoRequest transacaoAcaoRequest) {
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
        Optional<Carteira> optionalCarteira = carteiraRepository.verifyWallet(usuario.getId(), acao.getId());
        if (optionalCarteira.isPresent()) {
            carteira = optionalCarteira.get();
            carteira.setQuantidade(carteira.getQuantidade() + transacaoAcaoRequest.getQuantidade());
        } else {
            carteira = new Carteira();
            carteira.setUsuario(usuario);
            carteira.setAcao(acao);
            carteira.setQuantidade(transacaoAcaoRequest.getQuantidade());
        }
        carteiraRepository.save(carteira);

        registrarOperacao(transacaoAcaoRequest.getQuantidade(), usuario, acao, TipoOperacao.CREDITO);
    }

    public void vender(TransacaoAcaoRequest transacaoAcaoRequest) {
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
        Optional<Carteira> optionalCarteira = carteiraRepository.verifyWallet(usuario.getId(), acao.getId());
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
        carteiraRepository.save(carteira);

        registrarOperacao(transacaoAcaoRequest.getQuantidade(), usuario, acao, TipoOperacao.DEBITO);
    }

    private void registrarOperacao(Integer quantidade, Usuario usuario, Acao acao, TipoOperacao tipoOperacao) {
        Operacao operacao = new Operacao();
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "dataHora");
        operacao.setDataHora(LocalDateTime.now());
        operacao.setValor(historicoValorRepository.findCurrentByInitial(acao.getSigla(), pageRequest)
                .getContent().get(0).getValor());
        operacao.setQuantidade(quantidade);
        operacao.setUsuario(usuario);
        operacao.setAcao(acao);
        operacao.setTipo(tipoOperacao);
        operacaoRepository.save(operacao);
    }
}
