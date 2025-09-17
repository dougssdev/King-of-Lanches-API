package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosDetalhamentoHamburguer;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.repository.HamburguerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

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
}