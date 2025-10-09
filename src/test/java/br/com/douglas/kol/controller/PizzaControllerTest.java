package br.com.douglas.kol.controller;


import br.com.douglas.kol.dto.pizza.DadosAtualizacaoPizza;
import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.dto.pizza.DadosDetalhamentoPizza;
import br.com.douglas.kol.dto.pizza.DadosListagemPizza;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.service.PizzaService;
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

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(PizzaController.class)
class PizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PizzaService service;

    @Test
    @DisplayName("Given Pizza Register DTO When Adiciona Then Return Created HTTP Code")
    void testGiven_PizzaRegisterDTO_When_Adiciona_Then_ReturnCreatedHTTPCode() throws Exception {
        //Given

        DadosCadastroPizza data = new DadosCadastroPizza("Calabresa", new BigDecimal(45));
        DadosDetalhamentoPizza detailData = new DadosDetalhamentoPizza(1L, "Calabresa", new BigDecimal(45));

        given(service.salvar(data)).willReturn(detailData);

        //When

        ResultActions actions = mockMvc.perform(post("/pizza/adiciona")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));

        //Then

        actions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Calabresa")))
                .andExpect(jsonPath("$.preco", is(45)));
    }

    @Test
    @DisplayName("Given Empty Pizza Register DTO When Adicionar Then Return Bad Request HTTP Code")
    void testGiven_EmptyPizzaRegisterDTO_When_Adicionar_Then_ReturnBadRequestHTTPCode() throws Exception {
        //Given

        DadosCadastroPizza data = new DadosCadastroPizza("Calabresa", new BigDecimal(45));

        given(service.salvar(data)).willReturn(null);

        //When

        ResultActions actions = mockMvc.perform(post("/pizza/adiciona")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)));

        //Then

        actions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Falha ao salvar pizza"));
    }

    @Test
    @DisplayName("Given Pageable When Lista Then Return Ok Http Code")
    void testGiven_Pageable_When_Lista_Then_ReturnOkHttpCode() throws Exception {

        //Given
        Pageable pageable = PageRequest.of(0, 2);

        DadosListagemPizza pizza = new DadosListagemPizza(1L, "Calabresa", new BigDecimal(45));
        DadosListagemPizza pizza1 = new DadosListagemPizza(2L, "Pepperoni", new BigDecimal(45));

        Page<DadosListagemPizza> page = new PageImpl<>(List.of(pizza, pizza1), pageable, 2);

        given(service.listar(pageable)).willReturn(page);

        //When

        ResultActions actions = mockMvc.perform(get("/pizza/listar"));

        //Then

        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given Pizza Update Data When Atualiza Then Return Ok Http Code")
    void testGiven_PizzaUpdateData_When_Atualiza_Then_ReturnOkHttpCode() throws Exception {
        //Given

        DadosAtualizacaoPizza updateData =
                new DadosAtualizacaoPizza(1L, "Calabresa", new BigDecimal(45));

        DadosDetalhamentoPizza detailData =
                new DadosDetalhamentoPizza(1L, "Frango com Catupiry", new BigDecimal(50));

        given(service.atualiza(updateData)).willReturn(detailData);

        //When

        ResultActions actions = mockMvc.perform(put("/pizza/atualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)));

        //Then

        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Frango com Catupiry")));
    }

    @Test
    @DisplayName("Given PizzaID When Deletar Then Return No Content HTTP Code")
    void testGiven_PizzaID_When_Deletar_Then_ReturnNoContentHTTPCode() throws Exception {

        //Given

        Long pizzaID = 1L;

        willDoNothing().given(service).excluir(pizzaID);

        //When && Then

        mockMvc.perform(delete("/pizza/{id}", pizzaID))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

}