package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.pedido.DetalhamentoPedido;
import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.model.Pizza;
import br.com.douglas.kol.model.pedido.Pedido;
import br.com.douglas.kol.model.pedido.PedidoRegraNegocio;
import br.com.douglas.kol.model.pedido.StatusDoPedido;
import br.com.douglas.kol.repository.BebidaRepository;
import br.com.douglas.kol.repository.HamburguerRepository;
import br.com.douglas.kol.repository.PedidoRepository;
import br.com.douglas.kol.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pr;

    @Autowired
    private BebidaRepository br;

    @Autowired
    private PizzaRepository pzr;

    @Autowired
    private HamburguerRepository hr;

    @Autowired
    private PedidoRegraNegocio prn;

    @Transactional
    public DetalhamentoPedido criarNovoPedido(List<Long> idsBebida, List<Long> idsHamburguer, List<Long> idsPizza) throws RuntimeException {

        List<Bebida> bebidas = br.findBebidaIn(idsBebida);
        List<Hamburguer> hamburgueres = hr.findHamburguerIn(idsHamburguer);
        List<Pizza> pizzas = pzr.findPizzaIn(idsPizza);

        if (bebidas.isEmpty() && hamburgueres.isEmpty() && pizzas.isEmpty()) {
            throw new RuntimeException("Pedido vazio.");
        }

        var precoTotal = precoTotal(bebidas, hamburgueres, pizzas);

        Pedido novoPedido = new Pedido(bebidas, hamburgueres, pizzas, precoTotal);
        prn.confereDesconto(novoPedido);

        pr.save(novoPedido);

        return new DetalhamentoPedido(novoPedido);
    }

    public BigDecimal precoTotal(List<Bebida> bebidaList, List<Hamburguer> hamburguerList, List<Pizza> pizzaList) {

        var precoBebida = bebidaList.stream().map(Bebida::getPreco).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        var precoHamburguer = hamburguerList.stream().map(Hamburguer::getPreco).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        var precoPizza = pizzaList.stream().map(Pizza::getPreco).reduce(BigDecimal.valueOf(0), BigDecimal::add);

        return BigDecimal.ZERO.add(precoBebida).add(precoHamburguer).add(precoPizza);
    }

    public void cancelaPedido(Long id) {
        Optional<Pedido> byId = pr.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("Pedido não existente.");
        }
        byId.ifPresent(pedido -> pedido.setStatus(StatusDoPedido.Cancelado));
        pr.save(byId.get());
    }


}