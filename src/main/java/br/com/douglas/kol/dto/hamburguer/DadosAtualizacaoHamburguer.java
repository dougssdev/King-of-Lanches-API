package br.com.douglas.kol.dto.hamburguer;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoHamburguer(

        @NotNull
        Long id,

        String nome,

        BigDecimal preco

) {


}
