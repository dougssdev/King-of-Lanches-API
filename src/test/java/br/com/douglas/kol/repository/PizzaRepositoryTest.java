package br.com.douglas.kol.repository;

import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.model.Pizza;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("tests")
class PizzaRepositoryTest {

    @Autowired
    PizzaRepository repository;

    @Autowired
    EntityManager entityManager;

    //Deve retornar uma pizza com o ID 1
    @Test
    @DisplayName("Should return a Pizza with id number 1")
    void findPizzaCase1() {
        Long id = 1l;
        DadosCadastroPizza cadastroPizza =
                new DadosCadastroPizza("Calabresa", new BigDecimal(45.00));
        this.createPizza(cadastroPizza);
        Optional<Pizza> result = repository.findById(id);

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    //Não deve retornar nada
    @Test
    @DisplayName("Doesnt return anything")
    void findPizzaCase2() {
        Long id = 1l;

        Optional<Pizza> result = repository.findById(id);

        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    //Metodo para criar a pizza
    private Pizza createPizza(DadosCadastroPizza data) {
        Pizza newPizza = new Pizza(data);
        this.entityManager.persist(newPizza);
        return newPizza;
    }
}