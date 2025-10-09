package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.pizza.DadosAtualizacaoPizza;
import br.com.douglas.kol.dto.pizza.DadosCadastroPizza;
import br.com.douglas.kol.dto.pizza.DadosDetalhamentoPizza;
import br.com.douglas.kol.dto.pizza.DadosListagemPizza;
import br.com.douglas.kol.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaService service;

    @PostMapping("/adiciona")
    @Transactional
    public ResponseEntity<?> adiciona(@RequestBody @Valid DadosCadastroPizza dados,
                                   UriComponentsBuilder uriBuilder){
        DadosDetalhamentoPizza pizza = service.salvar(dados);

        if (pizza == null) {
            return ResponseEntity.badRequest().body("Falha ao salvar pizza");
        }

        var uri = uriBuilder.path("/pizza/{id}").buildAndExpand(pizza.id()).toUri();

        return ResponseEntity.created(uri).body(pizza);
    }

    @CrossOrigin
    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemPizza>> lista(@PageableDefault(size = 6 ,sort = "nome")
                                                              Pageable paginacao){
    Page<DadosListagemPizza> listagemPizzas = service.listar(paginacao);

    return ResponseEntity.ok(listagemPizzas);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<DadosDetalhamentoPizza> atualiza(@RequestBody @Valid DadosAtualizacaoPizza dados) {
    DadosDetalhamentoPizza atualiza = service.atualiza(dados);
    return ResponseEntity.ok(atualiza);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleta(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
