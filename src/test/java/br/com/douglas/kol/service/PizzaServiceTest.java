package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.pizza.DadosAtualizacaoPizza;
import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.dto.pizza.DadosDetalhamentoPizza;
import br.com.douglas.kol.dto.pizza.DadosListagemPizza;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTest {

    @Mock
    PizzaRepository repository;

    @InjectMocks
    @Autowired
    PizzaService service;


    @Test
    @DisplayName("Given DadosCadastroPizza Object when Salvar then Return DadosDetalhamentoPizza Object")
    void testGivenDadosCadastroPizzaObject_whenSalvar_thenReturnDadosDetalhamentoPizzaObject() {
        //Given
        DadosCadastroPizza data = new DadosCadastroPizza("Calabresa", new BigDecimal(50));

        //When

        DadosDetalhamentoPizza pizza = service.salvar(data);

        //Then

        assertNotNull(pizza);
        assertEquals(data.nome(), pizza.nome());
        assertEquals(data.preco(), pizza.preco());
        assertThat(pizza.preco()).isNotZero();
    }

    @Test
    @DisplayName("Given DadosCadastroPizza Object With Blank Name when Salvar then Throw a Illegal Argument Exception")
    void testGivenDadosCadastroPizzaObjectWithBlankName_whenSalvar_thenThrowIllegalArgumentExceptionIllegalArgumentException() {
        //Given
        DadosCadastroPizza data = new DadosCadastroPizza("", new BigDecimal(50));

        //When

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.salvar(data));

        //Then

        assertNotNull(exception);
        assertEquals("Name cannot be blank", exception.getMessage());
    }

    @Test
    @DisplayName("Given DadosCadastroPizza Object With Blank Name when Salvar then Throw a Illegal Argument Exception")
    void testGivenDadosCadastroPizzaObjectWithPriceBeingZero_whenSalvar_thenThrowIllegalArgumentException() {
        //Given
        DadosCadastroPizza data = new DadosCadastroPizza("Calabresa", new BigDecimal(0));

        //When

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.salvar(data));

        //Then

        assertNotNull(exception);
        assertEquals("Price cannot be zero", exception.getMessage());
    }

    @Test
    @DisplayName("Given Pageable Object when Listar then Return A Page With All Pizza")
    void testGivenPageableObject_whenListar_thenReturnAPageWithAllPizza() {
        //Given

        Pageable pageable = PageRequest.of(0, 2);
        Pizza pizza = new Pizza("Calabresa", new BigDecimal(45));
        Pizza pizza1 = new Pizza("Peperoni", new BigDecimal(45));

        Page<Pizza> page = new PageImpl<>(List.of(pizza, pizza1), pageable, 2);

        given(repository.findAllPizza(pageable)).willReturn(page);

        //When

        Page<DadosListagemPizza> pizzaPage = service.listar(pageable);

        //Then

        assertThat(pizzaPage.getContent()).hasSize(2);
        assertThat(pizzaPage.getContent().get(0).nome()).isEqualTo(pizza.getNome());
        assertThat(pizzaPage.getContent().get(1).nome()).isEqualTo(pizza1.getNome());
        assertThat(pizzaPage.getContent().get(1).preco()).isLessThan(new BigDecimal(50));
        assertThat(pizzaPage.getContent().get(0).preco()).isLessThan(new BigDecimal(50));
    }

    @Test
    @DisplayName("Given Pageable Object when Listar then Throw a Illegal Argument Exception")
    void testGivenAnEmptyPageableObject_whenListar_thenThrowIllegalArgumentException() {
        //Given

        Pageable pageable = PageRequest.of(0, 2);

        Page<Pizza> page = new PageImpl<>(List.of(), pageable, 2);

        given(repository.findAllPizza(pageable)).willReturn(page);

        //When && Then

        assertThatThrownBy(() -> service.listar(pageable))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("There is no pizza.");

    }

    @Test
    @DisplayName("Given Pizza Object When Atualizar Pizza Return Updated Pizza")
    void testGivenPizzaObjectDTO_When_AtualizarPizza_ReturnUpdatedPizza() {
        //Given
        Pizza pizza = new Pizza("Calabresa", new BigDecimal(45));

        given(repository.getReferenceById(1L)).willReturn(pizza);

        DadosAtualizacaoPizza dadosAtualizacaoPizza = new DadosAtualizacaoPizza(1L, "Portuguesa", new BigDecimal(60));

        //When

        DadosDetalhamentoPizza updated = service.atualiza(dadosAtualizacaoPizza);

        //Then

        assertNotNull(updated);
        assertThat(updated.nome()).isEqualTo(dadosAtualizacaoPizza.nome());
        assertThat(updated.preco()).isEqualTo(dadosAtualizacaoPizza.preco());
        assertThat(updated.nome()).isNotEqualTo("Calabresa");
    }

    @Test
    @DisplayName("Given Pizza Object DTO with IllegalArguments When Atualizar Pizza Then Throw a Illegal Argument Exception")
    void testGivenPizzaObjectDTOWithIllegalArguments_When_AtualizarPizza_thenThrowIllegalArgumentException() {
        //Given
        Pizza pizza = new Pizza("Calabresa", new BigDecimal(45));

        given(repository.getReferenceById(1L)).willReturn(pizza);

        DadosAtualizacaoPizza dadosAtualizacaoPizza = new DadosAtualizacaoPizza(1L, "", new BigDecimal(0));

        //When && Then

        assertThatThrownBy(() -> service.atualiza(dadosAtualizacaoPizza))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be blank");

    }

    @Test
    @DisplayName("Given Pizza Id When Deletar Then Return Nothing")
    void testGivenPizzaId_When_Deletar_ThenReturnNothing() {
        //Given

        Pizza pizza = new Pizza();
        pizza.setId_pizza(1L);

        willDoNothing().given(repository).deleteById(pizza.getId_pizza());

        //When

        service.excluir(1L);

        //Then

        verify(repository, times(1)).deleteById(pizza.getId_pizza());
    }
}