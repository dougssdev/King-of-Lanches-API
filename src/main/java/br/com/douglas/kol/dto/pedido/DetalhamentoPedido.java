package br.com.douglas.kol.dto.pedido;

import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.pedido.Pedido;
import br.com.douglas.kol.pedido.StatusDoPedido;
import br.com.douglas.kol.model.Pizza;

import java.math.BigDecimal;
import java.util.List;


public record DetalhamentoPedido(

        Long id_pedido,
        List<Bebida> bebida,
        List<Hamburguer> hamburguer,
        List<Pizza> pizza,
        StatusDoPedido status,
        BigDecimal precoTotal) {
    public DetalhamentoPedido(Pedido p) {
        this(p.getId(),
                p.getBebidas(),
                p.getHamburguers(),
                p.getPizzas(),
                p.getStatus(),
                p.getPrecoTotal());
    }


}
