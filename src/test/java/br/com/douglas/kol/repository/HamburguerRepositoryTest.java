package br.com.douglas.kol.repository;

import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.model.Hamburguer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HamburguerRepositoryTest {

    @Autowired
    HamburguerRepository repository;

    private Hamburguer hamburguer;

    @BeforeEach
    void setup() {
        DadosCadastroHamburguer data = new DadosCadastroHamburguer("X-Tudo", new BigDecimal(18.00));
        hamburguer = new Hamburguer(data);
    }

    @Test
    @DisplayName("Given HamburguerIdList When FindHamburguerIn Then Return HamburguerList")
    void testGivenHamburguerIdList_WhenFindHamburguerIn_ThenReturnHamburguerList() {
        //Given
        Hamburguer savedHamburguer = repository.save(hamburguer);
        List<Long> hamburguersIds = new ArrayList<>(List.of(savedHamburguer.getId_hamburguer(), 2l, 3l, 4l, 5l, 6l));

        //When
        List<Hamburguer> hamburguerIn = repository.findHamburguerIn(hamburguersIds);

        //Then
        assertNotNull(hamburguerIn);
        assertThat(hamburguerIn).contains(savedHamburguer);
    }

    @Test
    @DisplayName("Given HamburguerIdList When FindHamburguerInIsNull Then Throw NullPointerException")
    void testGivenHamburguerIdList_WhenFindHamburguerInIsNull_ThenThrowNullPointerException() {
        //Given
        Hamburguer saved = repository.save(hamburguer);
        List<Long> hamburguersIds = new ArrayList<>(List.of(2l, 3l, 4l, 5l, 6l));

        //When
        List<Hamburguer> hamburguerIn = repository.findHamburguerIn(hamburguersIds);

        //Then

        assertNotNull(hamburguerIn);
        assertThat(hamburguerIn).doesNotContain(saved);
        assertThrows(NullPointerException.class, () -> {
            if (!hamburguersIds.contains(saved.getId_hamburguer())) {
                throw new NullPointerException();
            }
        });
    }
}