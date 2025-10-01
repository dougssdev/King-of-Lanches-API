package br.com.douglas.kol.service;

import br.com.douglas.kol.dto.bebida.DadosListagemBebida;
import br.com.douglas.kol.model.Bebida;
import br.com.douglas.kol.dto.bebida.DadosAtualizacaoBebida;
import br.com.douglas.kol.dto.bebida.DadosCadastroBebida;
import br.com.douglas.kol.dto.bebida.DadosDetalhamentoBebida;
import br.com.douglas.kol.repository.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.douglas.kol.service.Check.checker;

@Service
public class BebidaService {

    @Autowired
    private BebidaRepository repository;

    public DadosDetalhamentoBebida salvar(DadosCadastroBebida dados){
        Bebida bebida = new Bebida(dados);
        checker(dados.nome(), dados.preco(), dados.quantidade());
        repository.save(bebida);
        return new DadosDetalhamentoBebida(bebida);
    }

    public Page<DadosListagemBebida> listar(Pageable paginacao){
        var page = repository.findAllByQuantidade(paginacao).map(DadosListagemBebida::new);
        if (page.isEmpty()) {
            throw new RuntimeException("There is no bebida");
        }
        return page;
    }

    public DadosDetalhamentoBebida atualizar(DadosAtualizacaoBebida dados){
        var bebida = repository.getReferenceById(dados.id());
        checker(dados.nome(), dados.preco(), dados.quantidade());

        bebida.setNome(dados.nome());
        bebida.setPreco(dados.preco());
        bebida.setQuantidade(dados.quantidade());

        return new DadosDetalhamentoBebida(bebida);
    }

    public void excluir(Long id){repository.deleteById(id);
    }

}
