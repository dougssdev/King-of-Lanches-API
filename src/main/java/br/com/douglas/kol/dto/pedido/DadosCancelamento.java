package br.com.douglas.kol.dto.pedido;

import br.com.douglas.kol.pedido.StatusDoPedido;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamento(
        @NotNull
        Long id,

        StatusDoPedido status
) {

}
