package br.com.douglas.kol.model;

import br.com.douglas.kol.dto.bebida.DadosAtualizacaoBebida;
import br.com.douglas.kol.dto.bebida.DadosCadastroBebida;
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
@Entity(name = "Bebida")
@Table (name = "bebida")
@NoArgsConstructor
@AllArgsConstructor
public class Bebida {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id_bebida;
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    @ManyToMany(mappedBy = "bebidas")
    @JsonIgnore
    private List<Pedido> pedidos;

    @Override
    public String toString() {
        return "Bebida: " + nome + "\n"
                + "Preço: " + preco
                + "\n";
    }

    public Bebida(DadosCadastroBebida dados) {
        this.nome = dados.nome();
        this.preco = dados.preco();
        this.quantidade = dados.quantidade();
    }

    public Bebida(Long id) {
    }

    public void atualizaInformacoes(DadosAtualizacaoBebida dados) {

        if(dados.nome() != null){
            this.nome = dados.nome();
        }

        if(dados.preco() != null){
            this.preco = dados.preco();
        }

        if (dados.quantidade() >= 0){
            this.quantidade = dados.quantidade();
        }
    }

}
