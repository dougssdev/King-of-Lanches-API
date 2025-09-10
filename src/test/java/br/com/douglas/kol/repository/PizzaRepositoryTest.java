package br.com.douglas.kol.repository;

import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.model.pedido.Pedido;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("tests")
class PizzaRepositoryTest {

    @Autowired
    PizzaRepository repository;

    @Autowired
    EntityManager entityManager;

    //Deve retornar uma pizza com o ID 1
    @Test
    @DisplayName("Should return a Pizza through the id")
    void findPizzaCase1() {
        createPizza(new DadosCadastroPizza("Calabresa", new BigDecimal(45)));

    }

    //Não deve retornar nada
    @Test
    @DisplayName("Doesnt return anything")
    void findPizzaCase2() {
        Long id = 1l;

        Optional<Pizza> result = repository.findById(id);

        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Return a pizza object")
    void findPizzaInCase1() {
        Long id = 1l;
        List<Long> ids = new ArrayList<>();

        ids.add(id);

        DadosCadastroPizza data = new DadosCadastroPizza("Presunto", new BigDecimal(45));
        this.createPizza(data);

        List<Pizza> pizzaList = repository.findPizzaIn(ids);

        Assertions.assertThat(pizzaList.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Display name")
    void testPizzaIdList_When_FindPizzaIn_ShouldReturnNull() {
        //Given


        //When

        //Then
    }

    //Metodo para criar a pizza
    private Pizza createPizza(DadosCadastroPizza data) {
        Pizza newPizza = new Pizza(data);
        this.entityManager.persist(newPizza);
        return newPizza;
    }
}