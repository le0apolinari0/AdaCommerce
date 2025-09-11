package br.com.AdaCommerce.repository;

import br.com.AdaCommerce.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Produto> findByCategoriaContainingIgnoreCase(String categoria, Pageable pageable);

    @Query("SELECT p FROM Produto p WHERE " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(p.categoria) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Produto> findByNomeOrCategoria(@Param("termo") String termo, Pageable pageable);

    @Query("SELECT p FROM Produto p WHERE " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND " +
            "LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    Page<Produto> findByNomeAndCategoria(@Param("nome") String nome,
                                         @Param("categoria") String categoria,
                                         Pageable pageable);
}

