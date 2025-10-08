package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.hamburguer.DadosAtualizacaoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosDetalhamentoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosListagemHamburguer;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.service.HamburguerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(HamburguerController.class)
class HamburguerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HamburguerService hamburguerService;


    @Test
    @DisplayName("Given Hamburguer Register DTO and Uri Components Builder When Adicionar Then Return Hamburguer Detail DTO")
    void testGiven_HamburguerRegisterDTO_When_Adicionar_Then_ReturnCreatedHttpCode() throws Exception {
        //Given

        DadosCadastroHamburguer data = new DadosCadastroHamburguer("X-tudo", new BigDecimal(22));
        DadosDetalhamentoHamburguer detailData =
                new DadosDetalhamentoHamburguer(1L, "X-tudo", new BigDecimal(22));

        given(hamburguerService.salvar(data)).willReturn(detailData);

        //When

        ResultActions actions = mockMvc.perform(post("/hamburguer/adiciona")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));

        //Then

        actions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.idHamburguer", is(1)))
                .andExpect(jsonPath("$.nome", is("X-tudo")))
                .andExpect(jsonPath("$.preco", is(22)))
        ;
    }

    @Test
    @DisplayName("Given Empty Hamburguer Register DTO When Adicionar Then Return Bad Request Http Code")
    void testGiven_EmptyHamburguerRegisterDTO_When_Adicionar_Then_ReturnBadRequestHttpCode() throws Exception {

        //Given

        DadosCadastroHamburguer data = new DadosCadastroHamburguer("X-tudo", new BigDecimal(22));

        given(hamburguerService.salvar(data)).willReturn(null);

        //When

        ResultActions actions = mockMvc.perform(post("/hamburguer/adiciona")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));

        //Then

        actions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Falha ao salvar hamburguer"));

    }

    @Test
    @DisplayName("Given Pageable  When Listar  Then Return Ok Http Code")
    void testGiven_Pageable_When_Listar_Then_ReturnOkHttpCode() throws Exception {

        //Given

        Pageable pageable = PageRequest.of(0, 2);
        DadosListagemHamburguer hamburguer0 = new DadosListagemHamburguer(1L, "X-Bacon", new BigDecimal(20));
        DadosListagemHamburguer hamburguer1 = new DadosListagemHamburguer(2l, "X-Egg", new BigDecimal(18));

        Page<DadosListagemHamburguer> page = new PageImpl<>(List.of(hamburguer0, hamburguer1), pageable, 2);

        given(hamburguerService.listar(pageable)).willReturn(page);

        //When

        ResultActions actions = mockMvc.perform(get("/hamburguer/listar"));

        //Then

        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given Hamburguer Update DTO When Atualiza Then Return Ok Http Code With Updated Hamburguer")
    void testGiven_HamburguerUpdateDTO_When_Atualiza_Then_ReturnOkHttpCodeWithUpdatedHamburguer() throws Exception {

        //Given

        DadosAtualizacaoHamburguer data = new DadosAtualizacaoHamburguer(1L, "X-TUDO", new BigDecimal(20));
        DadosDetalhamentoHamburguer detailData =
                new DadosDetalhamentoHamburguer(1L, "X-Calabresa", new BigDecimal(22));

        given(hamburguerService.atualizar(data)).willReturn(detailData);

        //When

        ResultActions actions = mockMvc.perform(put("/hamburguer/atualiza")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));

        //Then

        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given Hamburguer Id When Deletar Then Return No Content Http Code")
    void testGiven_HamburguerId_When_Deletar_Then_ReturnNoContentHttpCode() throws Exception {

        //Given
        Long hamburguerId = 1L;

        willDoNothing().given(hamburguerService).excluir(hamburguerId);

        //When && Then

        mockMvc.perform(delete("/hamburguer/{id}", hamburguerId))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

}