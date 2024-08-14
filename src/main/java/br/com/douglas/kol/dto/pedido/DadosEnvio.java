package br.com.douglas.kol.dto.pedido;

import java.util.List;

public record DadosEnvio(
        List<Long> idsBebida,
        List<Long> idsHamburguer,
        List<Long> idsPizza
) {

}
