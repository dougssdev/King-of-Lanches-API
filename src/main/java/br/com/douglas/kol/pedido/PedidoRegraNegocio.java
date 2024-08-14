package br.com.douglas.kol.pedido;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class PedidoRegraNegocio {
    public void confereDesconto(Pedido p){
        if(p.getPrecoTotal().compareTo(BigDecimal.valueOf(150)) > 0) {
            var desconto = p.getPrecoTotal().multiply(new BigDecimal(0.20));
            var valorDescontado = p.getPrecoTotal().subtract(desconto);
            var valorFinal=valorDescontado.round(new MathContext(5));

            p.setPrecoTotal(valorFinal);
        }
    }
}
