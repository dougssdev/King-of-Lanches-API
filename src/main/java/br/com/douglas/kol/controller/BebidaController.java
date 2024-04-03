package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.bebida.DadosListagemBebida;
import br.com.douglas.kol.service.BebidaService;
import br.com.douglas.kol.dto.bebida.DadosAtualizacaoBebida;
import br.com.douglas.kol.dto.bebida.DadosCadastroBebida;
import br.com.douglas.kol.dto.bebida.DadosDetalhamentoBebida;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
@RequestMapping ("/bebidas")
public class BebidaController {

    @Autowired
    private BebidaService service;
    @PostMapping("/adicionar")
    @Transactional
    public ResponseEntity adicionaBebida(@RequestBody @Valid DadosCadastroBebida dados,
                                         UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoBebida bebida = service.salvar(dados);

        var uri = uriBuilder.path("/bebidas/{id}").buildAndExpand(bebida.id()).toUri();

        return ResponseEntity.created((URI) uri).body(bebida);
    }

    @CrossOrigin
    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemBebida>> listarBebidas(@PageableDefault(size = 6, sort = {"id"}) Pageable paginacao) {
        Page<DadosListagemBebida> listagemBebida = service.listar(paginacao);
        return ResponseEntity.ok(listagemBebida);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizaBebida(@RequestBody @Valid DadosAtualizacaoBebida dados){
        DadosDetalhamentoBebida atualizar = service.atualizar(dados);
        return ResponseEntity.ok(atualizar);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirBebida(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
