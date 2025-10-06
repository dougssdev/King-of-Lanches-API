package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.pedido.DetalhamentoPedido;
import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.model.pedido.Pedido;
import br.com.douglas.kol.model.pedido.PedidoRegraNegocio;
import br.com.douglas.kol.model.pedido.StatusDoPedido;
import br.com.douglas.kol.repository.BebidaRepository;
import br.com.douglas.kol.repository.HamburguerRepository;
import br.com.douglas.kol.repository.PedidoRepository;
import br.com.douglas.kol.repository.PizzaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private BebidaRepository bebidaRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private HamburguerRepository hamburguerRepository;

    @Spy
    private PedidoRegraNegocio pedidoRegraNegocio;

    @Autowired
    @InjectMocks
    private PedidoService pedidoService;

    private List<Long> idsPizza;
    private List<Long> idsHamburguer;
    private List<Long> idsBebida;

    private Pizza pizza;
    private Hamburguer hamburguer;
    private Bebida bebida;
    private Pedido pedido;

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
    @DisplayName("Given FoodLists When CreatePedido Then Return SavedPedido With PrecoTotal")
    void testGivenFoodLists_WhenCreatePedido_ThenReturnSavedPedidoWithPrecoTotal() {
        //Given
        when(pizzaRepository.findPizzaIn(idsPizza)).thenReturn(List.of(pizza));
        when(hamburguerRepository.findHamburguerIn(idsHamburguer)).thenReturn(List.of(hamburguer));
        when(bebidaRepository.findBebidaIn(idsBebida)).thenReturn(List.of(bebida));

        //When
        DetalhamentoPedido novoPedido = pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        //Then
        assertNotNull(novoPedido);
        assertEquals(new BigDecimal(135), novoPedido.precoTotal());
    }

    @Test
    @DisplayName("Given PizzaList and HamburguerList When Criar Pedido Then Return Saved Pedido With Preco Total")
    void testGiven_PizzaListAndHamburguerList_When_CriarNovoPedido_Then_ReturnSavedPedidoWithPrecoTotal() {
        //Given
        when(pizzaRepository.findPizzaIn(idsPizza)).thenReturn(List.of(pizza));
        when(hamburguerRepository.findHamburguerIn(idsHamburguer)).thenReturn(List.of(hamburguer));
        when(bebidaRepository.findBebidaIn(idsBebida)).thenReturn(List.of());

        //When

        DetalhamentoPedido novoPedido = pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        //Then

        assertThat(novoPedido.bebida()).isEmpty();
        assertThat(novoPedido.precoTotal()).isEqualTo(new BigDecimal(90));
        assertNotNull(novoPedido);
        assertThat(novoPedido.status()).isEqualTo(StatusDoPedido.Enviado);
    }

    @Test
    @DisplayName("Given Bebida List and Hamburguer List When Criar Then Return Saved Pedido With Preco Total")
    void testGiven_BebidaListAndHamburguerList_When_CriarNovoPedido_Then_ReturnSavedPedidoWithPrecoTotal() {
        //Given
        when(bebidaRepository.findBebidaIn(idsBebida)).thenReturn(List.of(bebida));
        when(hamburguerRepository.findHamburguerIn(idsHamburguer)).thenReturn(List.of(hamburguer));
        when(pizzaRepository.findPizzaIn(idsPizza)).thenReturn(List.of());

        //When

        DetalhamentoPedido novoPedido = pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        //Then

        assertThat(novoPedido.pizza()).isEmpty();
        assertThat(novoPedido.bebida().get(0).getNome()).isEqualTo("Sprite");
        assertThat(novoPedido.bebida().get(0).getPreco()).isEqualTo(new BigDecimal(45));
        assertThat(novoPedido.precoTotal()).isEqualTo(new BigDecimal(90));
    }

    @Test
    @DisplayName("Given Bebida List and Pizza List When Criar Novo Pedido Then Return Saved Pedido With Preco Total")
    void testGiven_BebidaListAndPizzaList_When_CriarNovoPedido_Then_ReturnSavedPedidoWithPrecoTotal() {

        //Given
        when(bebidaRepository.findBebidaIn(idsBebida)).thenReturn(List.of(bebida));
        when(pizzaRepository.findPizzaIn(idsPizza)).thenReturn(List.of(pizza));
        when(hamburguerRepository.findHamburguerIn(idsHamburguer)).thenReturn(List.of());

        //When

        DetalhamentoPedido novoPedido = pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        //Then

        assertNotNull(novoPedido);
        assertThat(novoPedido.hamburguer()).isEmpty();
        assertThat(novoPedido.precoTotal()).isEqualTo(new BigDecimal(90));
        assertThat(novoPedido.pizza().get(0).getNome()).isEqualTo(pizza.getNome());
        assertThat(novoPedido.pizza().get(0).getPreco()).isEqualTo(pizza.getPreco());
    }

    @Test
    @DisplayName("Given Hamburguer List When Criar Novo Pedido Then Return Saved Pedido With Preco Total")
    void testGiven_HamburguerList_When_CriarNovoPedido_Then_ReturnSavedPedidoWithPrecoTotal() {

        //Given
        when(hamburguerRepository.findHamburguerIn(idsHamburguer)).thenReturn(List.of(hamburguer));
        when(pizzaRepository.findPizzaIn(idsPizza)).thenReturn(List.of());
        when(bebidaRepository.findBebidaIn(idsBebida)).thenReturn(List.of());

        //When

        DetalhamentoPedido novoPedido = pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        //Then

        assertNotNull(novoPedido);
        assertThat(novoPedido.pizza()).isEmpty();
        assertThat(novoPedido.bebida()).isEmpty();
        assertThat(novoPedido.hamburguer().get(0).getNome()).isEqualTo(hamburguer.getNome());
        assertThat(novoPedido.precoTotal()).isEqualTo(new BigDecimal(45));

    }

    @Test
    @DisplayName("Given Empty FoodLists When CreatePedido Should throw a RuntimeException")
    void testGivenEmptyFoodLists_WhenCreatePedido_ShouldThrowARuntimeException() {
        //Given

        List<Long> idsPizza = List.of();
        List<Long> idsHamburguer = List.of();
        List<Long> idsBebida = List.of();

        //When & Then

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);
        });

        assertEquals("Pedido vazio.", exception.getMessage());

    }

    @Test
    @DisplayName("Given FoodLists When CreatePedido With PrecoTotal Above 150.00 Should Give a 20 Percent Discount")
    void testGivenFoodLists_WhenCreatePedidoWithPrecoTotalAbove150_ShouldGiveA20PercentDiscount() {
        //Given

        pizza = new Pizza(1l, "Calabresa", new BigDecimal(80), new ArrayList<>());
        hamburguer = new Hamburguer(1l, "X-Mega", new BigDecimal(60), new ArrayList<>());
        bebida = new Bebida(1l, "Sprite", new BigDecimal(20), 100, new ArrayList<>());

        idsPizza = List.of(pizza.getId_pizza());
        idsHamburguer = List.of(hamburguer.getId_hamburguer());
        idsBebida = List.of(bebida.getId_bebida());

        when(pizzaRepository.findPizzaIn(idsPizza)).thenReturn(List.of(pizza));
        when(hamburguerRepository.findHamburguerIn(idsHamburguer)).thenReturn(List.of(hamburguer));
        when(bebidaRepository.findBebidaIn(idsBebida)).thenReturn(List.of(bebida));

        //When

        DetalhamentoPedido novoPedido = pedidoService.criarNovoPedido(idsBebida, idsHamburguer, idsPizza);

        //Then

        assertNotNull(novoPedido);
        verify(pedidoRegraNegocio, times(1)).confereDesconto(any(Pedido.class));
        assertEquals(0, novoPedido.precoTotal().compareTo(new BigDecimal(128)));
    }

    @Test
    @DisplayName("Given PedidoId When CancelaPedido Should Update PedidoStatus To 'Cancelado'")
    void testGivenPedidoId_WhenCancelaPedido_ShouldUpdatePedidoStatusToCancelado() {
        //Given

        pedido = new Pedido();
        pedido.setId(1l);
        pedido.setStatus(StatusDoPedido.Enviado);
        given(pedidoRepository.findById(1l)).willReturn(Optional.of(pedido));


        //When
        pedidoService.cancelaPedido(1l);

        //Then

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        verify(pedidoRepository).save(captor.capture());

        Pedido captorValue = captor.getValue();

        assertEquals(StatusDoPedido.Cancelado, captorValue.getStatus());
    }

    @Test
    @DisplayName("Given Non Existent PedidoId When Cancela Pedido Should Throw an RuntimeException")
    void testGivenNonExistentPedidoId_When_CancelaPedido_ShouldThrowAnRuntimeException() {
        //Given
        given(pedidoRepository.findById(1l)).willReturn(Optional.empty());

        //When & Then

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.cancelaPedido(1l);
        });

        assertEquals("Pedido não existente.", exception.getMessage());
    }

}