package br.com.douglas.kol.dto.hamburguer;

import br.com.douglas.kol.model.Hamburguer;

import java.math.BigDecimal;

public record DadosListagemHamburguer( Long id,
                                       String nome,
                                       BigDecimal preco) {

    public DadosListagemHamburguer(Hamburguer hamburguer){
        this(hamburguer.getId_hamburguer(), hamburguer.getNome(), hamburguer.getPreco());
    }
}
