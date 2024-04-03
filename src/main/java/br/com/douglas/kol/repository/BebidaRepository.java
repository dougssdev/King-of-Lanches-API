package br.com.douglas.kol.repository;

import br.com.douglas.kol.model.Bebida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long>{

    @Query
            ("""
            select b from Bebida b
            where
            b.quantidade > 0
            """)
    Page<Bebida> findAllByQuantidade (Pageable paginacao);

    @Query
            ("""
                    select b from Bebida b
                    where
                    b.id_bebida  IN (:id)
                    """)
    List<Bebida> findBebidaIn(@Param("id") List<Long> idBebida);

}
