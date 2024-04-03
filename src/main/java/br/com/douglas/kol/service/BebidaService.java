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

@Service
public class BebidaService {

    @Autowired
    private BebidaRepository repository;

    public DadosDetalhamentoBebida salvar(DadosCadastroBebida dados){
        Bebida bebida = new Bebida(dados);
        repository.save(bebida);
        return new DadosDetalhamentoBebida(bebida);
    }

    public Page<DadosListagemBebida> listar(Pageable paginacao){
        var page = repository.findAllByQuantidade(paginacao).map(DadosListagemBebida::new);
        return page;
    }

    public DadosDetalhamentoBebida atualizar(DadosAtualizacaoBebida dados){
        var bebida = repository.getReferenceById(dados.id());
        bebida.atualizaInformacoes(dados);
        return new DadosDetalhamentoBebida(bebida);
    }

    public void excluir(Long id){repository.deleteById(id);
    }

}
