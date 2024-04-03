package br.com.douglas.kol.dto.pizza;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroPizza(

        @NotBlank
        String nome,

        @NotNull
        BigDecimal preco
) {
}
