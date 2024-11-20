package br.ufms.cpcx.api.delivery.repositories;

import br.ufms.cpcx.api.delivery.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
//    @Query("SELECT r FROM Produto r " +
//            "WHERE (COALESCE(:descricao, '') = '' OR lower(r.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))) " +
//            "AND (COALESCE(:categoria, '') = '' OR lower(r.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')))")
//    Page<Produto> findAllByDescricaoAndCategoria(String country, String city, Pageable pageable);
}
