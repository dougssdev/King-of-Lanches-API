package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.hamburguer.DadosAtualizacaoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosCadastroHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosDetalhamentoHamburguer;
import br.com.douglas.kol.dto.hamburguer.DadosListagemHamburguer;
import br.com.douglas.kol.model.Hamburguer;
import br.com.douglas.kol.repository.HamburguerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HamburguerService {

    @Autowired
    HamburguerRepository repository;

    public DadosDetalhamentoHamburguer salvar (DadosCadastroHamburguer dados){
        Hamburguer hamburguer = new Hamburguer(dados);
        repository.save(hamburguer);
        return new DadosDetalhamentoHamburguer(hamburguer);
    }

    public Page<DadosListagemHamburguer> listar(Pageable paginacao) {
        var page = repository.findAllHamburguer(paginacao)
                .map(DadosListagemHamburguer :: new);
        return page;
    }

    public DadosDetalhamentoHamburguer atualizar(DadosAtualizacaoHamburguer dados) {
        var hamburguer = repository.getReferenceById(dados.id());
        hamburguer.atualizaInformacoes(dados);
        return new DadosDetalhamentoHamburguer(hamburguer);
    }

    public void excluir(Long id) {

        repository.deleteById(id);
    }
}
