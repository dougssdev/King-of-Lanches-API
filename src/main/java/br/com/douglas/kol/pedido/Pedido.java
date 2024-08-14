package br.com.douglas.kol.pedido;

import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.model.Pizza;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Pedido")
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "pedido_bebida",
            joinColumns = @JoinColumn(name = "pedido_id",
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "bebida_id",
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)))
    private List<Bebida> bebidas;

    @ManyToMany
    @JoinTable(name = "pedido_hamburguer",
            joinColumns = @JoinColumn(name = "pedido_id",
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "hamburguer_id",
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)))
    private List<Hamburguer> hamburguers;

    @ManyToMany
    @JoinTable(name = "pedido_pizza",
            joinColumns = @JoinColumn(name = "pedido_id",
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "pizza_id",
                    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)))
    private List<Pizza> pizzas;


    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
    private StatusDoPedido status;

    private BigDecimal precoTotal;

    public Pedido(List<Bebida> bebidas, List<Hamburguer> hamburgueres, List<Pizza> pizzas, BigDecimal precoTotal) {
        this.bebidas = bebidas;
        this.hamburguers = hamburgueres;
        this.pizzas = pizzas;
        this.precoTotal = precoTotal;
        this.status = StatusDoPedido.Enviado;
    }

}

