package br.com.douglas.kol.dto.hamburguer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroHamburguer(

        @NotBlank
        String nome,
        @NotNull
        BigDecimal preco

) {
}
