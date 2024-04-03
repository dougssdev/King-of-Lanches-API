package br.com.douglas.kol.dto.hamburguer;

import br.com.douglas.kol.model.Hamburguer;

import java.math.BigDecimal;

public record DadosDetalhamentoHamburguer(
        long idHamburguer,
        String nome,
        BigDecimal preco
) {
    public DadosDetalhamentoHamburguer(Hamburguer dados){
        this(dados.getId_hamburguer(), dados.getNome(), dados.getPreco());
    }
}
