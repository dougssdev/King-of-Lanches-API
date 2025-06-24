package br.com.douglas.kol.service;

import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.model.pedido.Pedido;
import br.com.douglas.kol.model.pedido.PedidoRegraNegocio;
import br.com.douglas.kol.repository.BebidaRepository;
import br.com.douglas.kol.repository.HamburguerRepository;
import br.com.douglas.kol.repository.PedidoRepository;
import br.com.douglas.kol.repository.PizzaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private BebidaRepository bebidaRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private HamburguerRepository hamburguerRepository;

    @Mock
    private PedidoRegraNegocio pedidoRegraNegocio;

    @Autowired
    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create a new order when everything is OK.")
    void criarNovoPedidoCase1() {

        List<Long> idsPizza = new ArrayList<>();
        List<Long> idsHamburguer = new ArrayList<>();
        List<Long> idsBebida = new ArrayList<>();

        List<Pedido> pedidos = new ArrayList<>();

        Pizza pizza = new Pizza(1l, "Calabresa", new BigDecimal(45), pedidos);
        Hamburguer hamburguer = new Hamburguer(1l, "X-Mega", new BigDecimal(45), pedidos);
        Bebida bebida = new Bebida(1l, "Sprite", new BigDecimal(45), 100, pedidos);

        idsBebida.add(bebida.getId_bebida());
        idsHamburguer.add(hamburguer.getId_hamburguer());
        idsPizza.add(pizza.getId_pizza());

        pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        verify(pedidoRepository, times(1)).save(any());

    }

}