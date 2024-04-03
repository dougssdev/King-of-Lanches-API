package br.com.douglas.kol.repository;

import br.com.douglas.kol.model.Hamburguer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HamburguerRepository extends JpaRepository<Hamburguer, Long> {


    @Query("""
        select h from Hamburguer h
""")
    Page<Hamburguer> findAllHamburguer(Pageable paginacao);

    @Query
            ("""
                    select h from Hamburguer h
                    where 
                    h.id_hamburguer IN (:id)
                    """)
    List<Hamburguer> findHamburguerIn (@Param("id") List<Long> idsHamburguer);
}
