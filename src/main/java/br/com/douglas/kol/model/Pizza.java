package br.com.douglas.kol.model;

import br.com.douglas.kol.dto.pizza.DadosAtualizacaoPizza;
import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.model.pedido.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity(name = "Pizza")
@Table (name = "pizza")
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id_pizza;
    private String nome;
    private BigDecimal preco;

    @ManyToMany(mappedBy = "pizzas")
    private List<Pedido> pedidos;

    @Override
    public String toString() {
        return "Pizza: " + nome + "\n"
                + "Preço: " + preco
                + "\n";

    }

    public Pizza(DadosCadastroPizza dados) {
        this.nome = dados.nome();
        this.preco = dados.preco();
    }

    public void atualizaInformacoes(DadosAtualizacaoPizza dados) {

        if(dados.nome() != null){
            this.nome = dados.nome();
        }

        if(dados.preco() != null){
            this.preco = dados.preco();
        }
    }
}
