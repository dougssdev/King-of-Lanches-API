package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.pedido.DadosCancelamento;
import br.com.douglas.kol.dto.pedido.DadosEnvio;
import br.com.douglas.kol.dto.pedido.DetalhamentoPedido;
import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.model.pedido.Pedido;
import br.com.douglas.kol.model.pedido.StatusDoPedido;
import br.com.douglas.kol.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoService pedidoService;

    private List<Long> idsPizza;
    private List<Long> idsHamburguer;
    private List<Long> idsBebida;

    private Pizza pizza;
    private Hamburguer hamburguer;
    private Bebida bebida;

    @BeforeEach
    void setup() {

        pizza = new Pizza(1l, "Calabresa", new BigDecimal(45), new ArrayList<>());
        hamburguer = new Hamburguer(1l, "X-Mega", new BigDecimal(45), new ArrayList<>());
        bebida = new Bebida(1l, "Sprite", new BigDecimal(45), 100, new ArrayList<>());

        idsPizza = List.of(pizza.getId_pizza());
        idsHamburguer = List.of(hamburguer.getId_hamburguer());
        idsBebida = List.of(bebida.getId_bebida());

    }

    @Test
    @DisplayName("Given Pedido Register DTO When Envia Pedido Then Return Saved Pedido")
    void testGiven_PedidoRegisterDTO_When_EnviaPedido_Then_ReturnSavedPedido() throws Exception {

        //Given
        DadosEnvio dados = new DadosEnvio(idsBebida, idsHamburguer, idsPizza);

        DetalhamentoPedido detalhamentoPedido = new DetalhamentoPedido(1L,
                List.of(bebida),
                List.of(hamburguer),
                List.of(pizza),
                StatusDoPedido.Enviado, new BigDecimal(135));

        given(pedidoService.criarNovoPedido(dados.idsBebida(), dados.idsHamburguer(), dados.idsPizza()))
                .willReturn(detalhamentoPedido);

        //When

        ResultActions resultActions = mockMvc.perform(post("/pedidos/enviaPedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dados)));

        //Then

        resultActions.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.precoTotal", is(135)));
    }

    @Test
    @DisplayName("Given Null Pedido Register DTO When Criar Pedido Then Return Bad Request")
    void testGiven_NullPedidoRegisterDTO_When_CriarPedido_Then_ReturnBadRequest() throws Exception {

        //Given

        DadosEnvio dadosEnvio = new DadosEnvio(idsBebida, idsHamburguer, idsPizza);

        given(pedidoService.criarNovoPedido(dadosEnvio.idsBebida(), dadosEnvio.idsHamburguer(), dadosEnvio.idsPizza()))
                .willReturn(null);

        //When

        ResultActions actions = mockMvc.perform(post("/pedidos/enviaPedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosEnvio)));


        //Then

        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Given Cancel Pedido Data When CancelarPedido Then Return Pedido With Cancelado Status")
    void testGiven_CancelPedidoData_When_CancelarPedido_Then_ReturnPedidoWithCanceladoStatus() throws Exception {

        //Given

        Pedido pedido = new Pedido(1L,
                List.of(bebida),
                List.of(hamburguer),
                List.of(pizza),
                StatusDoPedido.Cancelado,
                new BigDecimal(100));

        DadosCancelamento dadosCancelamento = new DadosCancelamento(1L, StatusDoPedido.Cancelado);

        given(pedidoService.cancelaPedido(dadosCancelamento.id())).willReturn(Optional.of(pedido));

        //When

        ResultActions actions = mockMvc.perform(post("/pedidos/cancelaPedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosCancelamento)));


        //Then

        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(StatusDoPedido.Cancelado.toString())));
    }

}