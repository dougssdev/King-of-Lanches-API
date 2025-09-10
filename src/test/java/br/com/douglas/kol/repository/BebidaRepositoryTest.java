package br.com.douglas.kol.repository;

import br.com.douglas.kol.dto.bebida.DadosCadastroBebida;
import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.model.Bebida;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BebidaRepositoryTest {

    @Autowired
    BebidaRepository repository;

    private Bebida bebida;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        DadosCadastroBebida data = new DadosCadastroBebida("Sprite", new BigDecimal(7), 100);
        bebida = new Bebida(data);
    }

    @Test
    @DisplayName("Given a BebidasList, find bebida in List ")
    void testGivenBebidaID_whenFindBebidaInIdsList_ShouldReturnBebidaList() {
        //Given

        Bebida savedBebida = repository.save(bebida);
        List<Long> bebidasIds = new ArrayList<>(List.of(savedBebida.getId_bebida(), 3l, 5l, 2l, 6l));
        //When
        List<Bebida> result = repository.findBebidaIn(bebidasIds);
        //Then
        assertNotNull(result);
        assertThat(result).contains(savedBebida);
    }

    @Test
    @DisplayName("Given a BebidasList, when do not find bebida in list, should throw exception")
    void testGivenBebidaId_WhenBebidaInIdsListIsNull_Should_ThrowAnException() {
        //Given
        Bebida savedBebida = repository.save(bebida);
        List<Long> bebidasIds = new ArrayList<>(List.of(3l, 5l, 2l, 6l));
        //When
        List<Bebida> result = repository.findBebidaIn(bebidasIds);
        //Then

        assertNotNull(result);
        assertThat(result).doesNotContain(savedBebida);
        assertThrows(NullPointerException.class, () -> {
            if (!bebidasIds.contains(savedBebida.getId_bebida())) {
                throw new NullPointerException();
            }
        });
    }

}