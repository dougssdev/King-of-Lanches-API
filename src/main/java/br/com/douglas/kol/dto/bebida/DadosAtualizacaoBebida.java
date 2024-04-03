package br.com.douglas.kol.dto.bebida;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoBebida( @NotNull
                                      Long id,
                                      String nome,
                                      BigDecimal preco,
                                      int quantidade
                                      ){



}
