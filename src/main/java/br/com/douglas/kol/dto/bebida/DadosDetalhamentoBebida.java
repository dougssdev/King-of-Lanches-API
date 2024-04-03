package br.com.douglas.kol.dto.bebida;

import br.com.douglas.kol.model.Bebida;

import java.math.BigDecimal;

public record DadosDetalhamentoBebida(
        long id,
        String nome,
        BigDecimal preco,
        int quantidade) {

    public  DadosDetalhamentoBebida (Bebida bebida){
      this(bebida.getId_bebida(), bebida.getNome(), bebida.getPreco(), bebida.getQuantidade());
    }

}
