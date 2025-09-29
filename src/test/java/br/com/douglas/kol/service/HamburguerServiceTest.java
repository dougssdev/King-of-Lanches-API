package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosDetalhamentoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosListagemHamburguer;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.repository.HamburguerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class HamburguerServiceTest {

    @Mock
    HamburguerRepository repository;

    @Autowired
    @InjectMocks
    HamburguerService hamburguerService;

    private DadosCadastroHamburguer hamburguer;

    @BeforeEach
    void setup() {
        hamburguer = new DadosCadastroHamburguer("X-Tudo", new BigDecimal(35));
    }

    @Test
    @DisplayName("Given DTO Cadastro Hamburguer When Salvar Return DTO Detalhamento Hamburguer")
    void testGivenDTOCadastroHamburguer_When_Salvar_ReturnDTODetalhamentoHamburguer() {

        //When
        DadosDetalhamentoHamburguer salvar = hamburguerService.salvar(hamburguer);

        //Then
        assertNotNull(salvar);
        assertEquals(hamburguer.nome(), salvar.nome());
        assertEquals(hamburguer.preco(), salvar.preco());
    }

    @Test
    @DisplayName("Given DTOCadastroHamburguer Blank Name When Salvar Should Throw RunTimeException")
    void testGivenDTOCadastroHamburguerBlankName_When_Salvar_ShouldThrowRunTimeException() {

        //Given
        hamburguer = new DadosCadastroHamburguer(" ", new BigDecimal(0));

        //When & Then

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            hamburguerService.salvar(hamburguer);
        });

        assertEquals("Name cannot be blank", exception.getMessage());

    }

    @Test
    @DisplayName("Given DTOCadastroHamburguer with Price being zero When Salvar Should Throw RunTimeException")
    void testGivenDTOCadastroHamburguerWithPriceBeingZero_When_Salvar_ShouldThrowRunTimeException() {

        //Given
        hamburguer = new DadosCadastroHamburguer("X-tudo", new BigDecimal(0));

        //When & Then

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            hamburguerService.salvar(hamburguer);
        });

        assertEquals("Price cannot be zero", exception.getMessage());

    }

    @Test
    @DisplayName("Given a Page when Listar should return all Hamburguer")
    void testGivenAPage_WhenListar_Should_ReturnAllHamburguer() {
        //Given
        Pageable pageable = PageRequest.of(0, 2);
        Hamburguer hamburguer0 = new Hamburguer("X-Bacon", new BigDecimal(40));
        Hamburguer hamburguer1 = new Hamburguer("X-Egg", new BigDecimal(40));


        Page<Hamburguer> page =
                new PageImpl<>(List.of(hamburguer0, hamburguer1), pageable, 2);

        given(repository.findAllHamburguer(pageable)).willReturn(page);

        //When

        Page<DadosListagemHamburguer> result = hamburguerService.listar(pageable);

        //Then

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).nome()).isEqualTo("X-Bacon");
        assertThat(result.getContent().get(1).nome()).isEqualTo("X-Egg");
    }

    @Test
    @DisplayName("Given an Empty Page when Listar should throw a Runtime Exception")
    void testGivenAnEmpty_Page_WhenListar_shouldThrowARuntimeException() {

        //Given
        Pageable pageable = PageRequest.of(0, 2);
        Page<Hamburguer> page =
                new PageImpl<>(List.of(), pageable, 2);

        given(repository.findAllHamburguer(pageable)).willReturn(page);
        //When

        RuntimeException exception = assertThrows(RuntimeException.class, () -> hamburguerService.listar(pageable));

        //Then

        assertEquals("Não há hamburguer disponível.", exception.getMessage());
    }


}