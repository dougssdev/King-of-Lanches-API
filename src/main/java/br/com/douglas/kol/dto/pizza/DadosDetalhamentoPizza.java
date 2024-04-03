package br.com.douglas.kol.dto.pizza;

import br.com.douglas.kol.model.Pizza;

import java.math.BigDecimal;

public record DadosDetalhamentoPizza(

        Long id,

        String nome,

        BigDecimal preco

) {
    public DadosDetalhamentoPizza(Pizza pizza) {
        this(pizza.getId_pizza(), pizza.getNome(), pizza.getPreco());
    }
}
