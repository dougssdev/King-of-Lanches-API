package br.com.douglas.kol.model;

import br.com.douglas.kol.dto.hamburguer.DadosAtualizacaoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.model.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity(name = "Hamburguer")
@Table (name = "hamburguer")
@NoArgsConstructor
@AllArgsConstructor
public class Hamburguer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id_hamburguer;

    private String nome;
    private BigDecimal preco;

    @ManyToMany(mappedBy = "hamburguers")
    @JsonIgnore
    private List<Pedido> pedidos;

    public Hamburguer(String s, BigDecimal bigDecimal) {
        this.nome = s;
        this.preco = bigDecimal;
    }

    @Override
    public String toString() {
        return "Hamburguer: " + nome + "\n"
                + "Preço: " + preco
                + "\n";
    }

    public Hamburguer(DadosCadastroHamburguer dados) {
        this.nome = dados.nome();
        this.preco = dados.preco();
    }

}
