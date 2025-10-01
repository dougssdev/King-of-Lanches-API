package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.bebida.DadosAtualizacaoBebida;
import br.com.douglas.kol.dto.bebida.DadosCadastroBebida;
import br.com.douglas.kol.dto.bebida.DadosDetalhamentoBebida;
import br.com.douglas.kol.dto.bebida.DadosListagemBebida;
import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.repository.BebidaRepository;
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
class BebidaServiceTest {

    @Mock
    BebidaRepository repository;

    @InjectMocks
    @Autowired
    BebidaService service;

    @Test
    @DisplayName("Given DTOCadastroBebida When Salvar Then Return DTODetalhamentoBebida Object")
    void testGiven_DTOCadastroBebida_When_Salvar_Then_ReturnDTODetalhamentoBebida() {
        //Given

        DadosCadastroBebida dados = new DadosCadastroBebida("Sprite", new BigDecimal(10), 100);

        //When

        DadosDetalhamentoBebida result = service.salvar(dados);

        //Then
        assertNotNull(result);
        assertEquals(dados.nome(), result.nome());
    }

    @Test
    @DisplayName("Given DTOCadastroBebida With Blank Name When Salvar Throws IllegalArgumentException")
    void testGiven_DTOCadastroBebidaWithBlankName_When_Salvar_Throws_IllegalArgumentException() {

        //Given

        DadosCadastroBebida dados = new DadosCadastroBebida(" ", new BigDecimal(10), 100);

        //When & Then

        assertThatThrownBy(() -> service.salvar(dados))
                .hasMessage("Name cannot be blank")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Given DTOCadastroBebida With Price Being Zero When Salvar Throws IllegalArgumentException")
    void testGiven_DTOCadastroBebidaPriceZero_When_Salvar_Throws_IllegalArgumentException() {

        //Given

        DadosCadastroBebida dados = new DadosCadastroBebida("Sprite", new BigDecimal(0), 100);

        //When & Then

        assertThatThrownBy(() -> service.salvar(dados))
                .hasMessage("Price cannot be zero")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Given DTOCadastroBebida With Quantity Being Zero When Salvar Throws IllegalArgumentException")
    void testGiven_DTOCadastroBebidaQuantidadeZero_When_Salvar_Throws_IllegalArgumentException() {

        //Given

        DadosCadastroBebida dados = new DadosCadastroBebida("Sprite", new BigDecimal(10), 0);

        //When & Then

        assertThatThrownBy(() -> service.salvar(dados))
                .hasMessage("Quantity cannot be zero")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Given Pageable Object When Listar Then Return Page of Dados Listagem Bebida ")
    void testGiven_PageableObject_When_Listar_Then_ReturnPageOfDadosListagemBebida() {

        //Given
        Pageable pageable = PageRequest.of(0, 2);

        Bebida b1 = new Bebida("Sprite", new BigDecimal(10), 100);
        Bebida b2 = new Bebida("Coca-cola", new BigDecimal(10), 100);

        Page<Bebida> page = new PageImpl<>(List.of(b1, b2), pageable, 2);

        given(repository.findAllByQuantidade(pageable)).willReturn(page);

        //When

        Page<DadosListagemBebida> bebidas = service.listar(pageable);

        //Then

        assertThat(bebidas).hasSize(2);

        assertThat(bebidas.getContent().get(0).nome()).isEqualTo("Sprite");
        assertThat(bebidas.getContent().get(0).preco()).isEqualTo(new BigDecimal(10));

        assertThat(bebidas.getContent().get(1).nome()).isEqualTo("Coca-cola");
        assertThat(bebidas.getContent().get(1).preco()).isEqualTo(new BigDecimal(10));

        assertThat(bebidas.getContent().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Given Empty Pageable Object When Listar Then Throw RuntimeException ")
    void testGiven_EmptyPageableObject_When_Listar_Then_ThrowRuntimeException() {

        //Given
        Pageable pageable = PageRequest.of(0, 2);

        Page<Bebida> page = new PageImpl<>(List.of(), pageable, 2);

        given(repository.findAllByQuantidade(pageable)).willReturn(page);

        //When & Then

        assertThatThrownBy(() -> service.listar(pageable))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("There is no bebida");

    }

    @Test
    @DisplayName("Given_When_Then")
    void testGiven_DadosAtualizacaoBebida_When_Atualizar_Then_ReturnUpdatedBebida() {
        //Given

        Bebida bebida = new Bebida("Coca-cola", new BigDecimal(20), 80);

        given(repository.getReferenceById(1L)).willReturn(bebida);

        DadosAtualizacaoBebida atualizacaoBebida =
                new DadosAtualizacaoBebida(1L, "Sprite", new BigDecimal(10), 100);

        //When

        DadosDetalhamentoBebida updated = service.atualizar(atualizacaoBebida);

        //Then

        assertNotNull(updated);
        assertThat(updated.nome()).isNotEqualTo("Coca-cola");
        assertThat(updated.preco()).isNotEqualTo(new BigDecimal(20));
    }

    @Test
    @DisplayName("Given Empty Values When Atualizar Then Throw Illegal Argument Exception")
    void testGiven_EmptyValues_When_Atualizar_Then_ThrowIllegalArgumentException() {
        //Given

        Bebida bebida = new Bebida("Coca-cola", new BigDecimal(20), 80);

        given(repository.getReferenceById(1L)).willReturn(bebida);

        DadosAtualizacaoBebida atualizacaoBebida =
                new DadosAtualizacaoBebida(1L, " ", new BigDecimal(0), 0);

        //When && Then

        assertThatThrownBy(() -> service.atualizar(atualizacaoBebida))
                .isInstanceOf(IllegalArgumentException.class)
                .isNotNull()
                .hasMessage("Name cannot be blank");

    }

    @Test
    @DisplayName("Given BebidaId When Excluir Then Do Nothing")
    void testGiven_BebidaId_When_Excluir_Then_DoNothing() {
        //Given
        Bebida bebida = new Bebida(1L);

        willDoNothing().given(repository).deleteById(bebida.getId_bebida());

        //When

        service.excluir(bebida.getId_bebida());

        //Then

        verify(repository, times(1)).deleteById(bebida.getId_bebida());

    }

}