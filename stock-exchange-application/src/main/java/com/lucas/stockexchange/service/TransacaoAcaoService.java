package com.lucas.stockexchange.service;

import com.lucas.stockexchange.domain.model.Acao;
import com.lucas.stockexchange.domain.model.Carteira;
import com.lucas.stockexchange.domain.model.Usuario;
import com.lucas.stockexchange.domain.repository.AcaoRepository;
import com.lucas.stockexchange.domain.repository.CarteiraRepository;
import com.lucas.stockexchange.domain.repository.UsuarioRepository;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransacaoAcaoService {
    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;
    private final AcaoRepository acaoRepository;

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
    }
}
