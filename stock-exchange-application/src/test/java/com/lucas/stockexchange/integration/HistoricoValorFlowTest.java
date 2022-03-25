package com.lucas.stockexchange.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lucas.stockexchange.domain.model.TipoAcao;
import com.lucas.stockexchange.dto.acao.AcaoRequest;
import com.lucas.stockexchange.dto.acao.AcaoResponse;
import com.lucas.stockexchange.dto.carteira.CarteiraResponse;
import com.lucas.stockexchange.dto.setor.SetorRequest;
import com.lucas.stockexchange.dto.setor.SetorResponse;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoRequest;
import com.lucas.stockexchange.dto.transacaoacao.TransacaoAcaoResponse;
import com.lucas.stockexchange.dto.usuario.UsuarioRequest;
import com.lucas.stockexchange.dto.usuario.UsuarioResponse;
import com.lucas.stockexchange.service.AjustarValorAcao;
import com.lucas.stockexchange.tools.RestResponsePage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HistoricoValorFlowTest {

    @Autowired
    private AjustarValorAcao ajustarValorAcao;

    @LocalServerPort
    private int porta;

    @BeforeEach
    public void setup() {
        RestAssured.port = porta;
    }

    @Test
    public void deveVerificarSeValorOscila() {
        SetorResponse setorResponse = cadastroSetor();
        AcaoResponse acaoResponse = cadastroAcao(setorResponse);
        cadastroUsuario();
        executarPedidoDeCompra();
        executarPedidoDeVenda();
        ajustarValorAcao.executarAjustes();
        verificaQuantidadeCarteira();
        Response response = RestAssured.given().get("/acao/" + acaoResponse.getId());
        Assertions.assertEquals(200, response.statusCode());
        acaoResponse = response.as(AcaoResponse.class);
        Assertions.assertEquals(BigDecimal.valueOf(375).stripTrailingZeros(), acaoResponse.getValor().stripTrailingZeros());
    }

    private Response verificaQuantidadeCarteira() {
        Response response = RestAssured.given().param("cpf", "12345678901").get("/carteira");
        Assertions.assertEquals(200, response.statusCode());
        TypeReference<RestResponsePage<CarteiraResponse>> referenciaDeTipo = new TypeReference<>() {};
        RestResponsePage<CarteiraResponse> page = response.as(referenciaDeTipo.getType());
        Assertions.assertEquals(1, page.getTotalElements());
        Assertions.assertEquals(50, (page.getContent().get(0)).getQuantidade());
        return response;
    }

    private TransacaoAcaoResponse executarPedidoDeVenda() {
        TransacaoAcaoRequest transacaoAcaoRequest = new TransacaoAcaoRequest();
        transacaoAcaoRequest.setLogin("lucas123");
        transacaoAcaoRequest.setSigla("PFRM3");
        transacaoAcaoRequest.setValor(BigDecimal.valueOf(250));
        transacaoAcaoRequest.setQuantidade(50);
        transacaoAcaoRequest.setPrazo(LocalDateTime.now());
        Response response = RestAssured.given().contentType(ContentType.JSON).body(transacaoAcaoRequest).post("/vender");
        Assertions.assertEquals(201, response.statusCode());
        return response.as(TransacaoAcaoResponse.class);
    }

    private TransacaoAcaoResponse executarPedidoDeCompra() {
        TransacaoAcaoRequest transacaoAcaoRequest = new TransacaoAcaoRequest();
        transacaoAcaoRequest.setLogin("lucas123");
        transacaoAcaoRequest.setSigla("PFRM3");
        transacaoAcaoRequest.setValor(BigDecimal.valueOf(250));
        transacaoAcaoRequest.setQuantidade(100);
        transacaoAcaoRequest.setPrazo(LocalDateTime.now());
        Response response = RestAssured.given().contentType(ContentType.JSON).body(transacaoAcaoRequest).post("/comprar");
        Assertions.assertEquals(201, response.statusCode());
        return response.as(TransacaoAcaoResponse.class);
    }

    private UsuarioResponse cadastroUsuario() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome("lucas");
        usuarioRequest.setLogin("lucas123");
        usuarioRequest.setSenha("sacul");
        usuarioRequest.setCpf("12345678901");
        Response response = RestAssured.given().contentType(ContentType.JSON).body(usuarioRequest).post("/usuario");
        Assertions.assertEquals(201, response.statusCode());
        UsuarioResponse usuarioResponse = response.as(UsuarioResponse.class);
        Assertions.assertNotNull(usuarioResponse.getId());
        return usuarioResponse;
    }

    private AcaoResponse cadastroAcao(SetorResponse setorResponse) {
        AcaoRequest acaoRequest = new AcaoRequest();
        acaoRequest.setNome("profarma");
        acaoRequest.setSigla("PFRM3");
        acaoRequest.setValor(BigDecimal.valueOf(1000000));
        acaoRequest.setQuantidade(4000);
        acaoRequest.setTipo(TipoAcao.ORDINARIA);
        acaoRequest.setSetorId(setorResponse.getId());
        Response response = RestAssured.given().contentType(ContentType.JSON).body(acaoRequest).post("/acao");
        Assertions.assertEquals(201, response.statusCode());
        AcaoResponse acaoResponse = response.as(AcaoResponse.class);
        Assertions.assertEquals(BigDecimal.valueOf(250).stripTrailingZeros(), acaoResponse.getValor().stripTrailingZeros());
        response = RestAssured.given().get("/acao/" + acaoResponse.getId());
        Assertions.assertNotNull(acaoResponse.getId());
        return acaoResponse;
    }

    private SetorResponse cadastroSetor() {
        SetorRequest setorRequest = new SetorRequest();
        setorRequest.setNome("saude");
        setorRequest.setDescricao("medicamentos e outros produtos; serviços medicos, hospitalares, de analises e diagnosticos; equipamentos; comercio e distribuiçao");
        Response response = RestAssured.given().contentType(ContentType.JSON).body(setorRequest).post("/setor");
        Assertions.assertEquals(201, response.statusCode());
        SetorResponse setorResponse = response.as(SetorResponse.class);
        Assertions.assertNotNull(setorResponse.getId());
        return setorResponse;
    }
}
