package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.pizza.DadosAtualizacaoPizza;
import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.dto.pizza.DadosDetalhamentoPizza;
import br.com.douglas.kol.dto.pizza.DadosListagemPizza;
import br.com.douglas.kol.repository.PizzaRepository;
import br.com.douglas.kol.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.douglas.kol.service.Check.checker;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository repository;


    public DadosDetalhamentoPizza salvar(DadosCadastroPizza dados) {
        Pizza pizza = new Pizza(dados);
        checker(dados.nome(), dados.preco());
        repository.save(pizza);
        return new DadosDetalhamentoPizza(pizza);
    }

    public Page<DadosListagemPizza> listar(Pageable paginacao) {
      var page = repository.findAllPizza(paginacao).map(DadosListagemPizza :: new);
        if (page.isEmpty()) {
            throw new RuntimeException("There is no pizza.");
        }
       return page;
    }

    public DadosDetalhamentoPizza atualiza(DadosAtualizacaoPizza dados) {
        var pizza = repository.getReferenceById(dados.id());
        checker(dados.nome(), dados.preco());
        pizza.setNome(dados.nome());
        pizza.setPreco(dados.preco());
        return new DadosDetalhamentoPizza(pizza);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
