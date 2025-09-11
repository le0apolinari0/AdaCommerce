package br.com.AdaCommerce.service.interfacy;

import br.com.AdaCommerce.dto.request.ProdutoRequest;
import br.com.AdaCommerce.dto.response.ProdutoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {
    ProdutoResponse criarProduto(ProdutoRequest request);
    ProdutoResponse buscarPorId(Long id);
    Page<ProdutoResponse> listarProdutos(Pageable pageable);
    Page<ProdutoResponse> buscarPorNome(String nome, Pageable pageable);
    Page<ProdutoResponse> buscarPorCategoria(String categoria, Pageable pageable);
    Page<ProdutoResponse> buscarPorNomeOuCategoria(String termo, Pageable pageable);
    Page<ProdutoResponse> buscarPorNomeECategoria(String nome, String categoria, Pageable pageable);
    ProdutoResponse atualizarProduto(Long id, ProdutoRequest request);
    ProdutoResponse importarProdutoPorCodigoBarras(String codigoBarras);
}

