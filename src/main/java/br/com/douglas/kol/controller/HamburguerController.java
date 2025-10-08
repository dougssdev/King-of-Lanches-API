package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.hamburguer.DadosAtualizacaoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosDetalhamentoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosListagemHamburguer;
import br.com.douglas.kol.service.HamburguerService;
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
@RequestMapping("/hamburguer")
public class HamburguerController {

    @Autowired
    private HamburguerService service;

    @PostMapping("/adiciona")
    @Transactional
    public ResponseEntity<?> adicionar(@RequestBody @Valid DadosCadastroHamburguer dados,
                                    UriComponentsBuilder uriBuilder){

        DadosDetalhamentoHamburguer hamburguer = service.salvar(dados);

        if (hamburguer == null) {
            return ResponseEntity.badRequest().body("Falha ao salvar hamburguer");
        }

        var uri = uriBuilder.path("/hamburguer/{id}").buildAndExpand(hamburguer.idHamburguer()).toUri();

        return  ResponseEntity.created(uri).body(hamburguer);
    }

    @CrossOrigin
    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemHamburguer>> listar(
            @PageableDefault(sort = ("id"), size = 10)Pageable paginacao){
   Page<DadosListagemHamburguer> listagemHamburguer = service.listar(paginacao);
    return ResponseEntity.ok(listagemHamburguer);
    }

    @PutMapping("/atualiza")
    public ResponseEntity<?> atualiza(@RequestBody @Valid DadosAtualizacaoHamburguer dados) {
        DadosDetalhamentoHamburguer atualiza = service.atualizar(dados);
        return ResponseEntity.ok(atualiza);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
