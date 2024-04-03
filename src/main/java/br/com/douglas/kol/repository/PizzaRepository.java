package br.com.douglas.kol.repository;

import br.com.douglas.kol.model.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    @Query
    ("""
    Select p  from Pizza p
""")
    Page<Pizza> findAllPizza(Pageable paginacao);

    @Query
            ("""
                    select p from Pizza p
                    where 
                    p.id_pizza IN (:id) 
                    """)
    List<Pizza> findPizzaIn(@Param("id") List<Long> pizzaList);
}
