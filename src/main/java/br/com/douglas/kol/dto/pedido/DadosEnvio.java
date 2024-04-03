package br.com.douglas.kol.dto.pedido;

import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.model.Pizza;

import java.util.List;

public record DadosEnvio(
        List<Long> idsBebida,
        List<Long> idsHamburguer,
        List<Long> idsPizza
) {

}
