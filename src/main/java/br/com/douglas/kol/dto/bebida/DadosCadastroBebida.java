package br.com.douglas.kol.dto.bebida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public record DadosCadastroBebida(
                                  @NotBlank
                                 String nome,
                                  @NotNull
                                 BigDecimal preco,
                                  @NotNull
                                  int quantidade
)

{
}
