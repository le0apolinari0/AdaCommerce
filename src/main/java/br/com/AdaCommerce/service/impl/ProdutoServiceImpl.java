package br.com.AdaCommerce.service.impl;

import br.com.AdaCommerce.dto.request.ProdutoRequest;
import br.com.AdaCommerce.dto.response.ProdutoResponse;
import br.com.AdaCommerce.execption.BusinessException;
import br.com.AdaCommerce.mapper.ProdutoMapper;
import br.com.AdaCommerce.model.Produto;
import br.com.AdaCommerce.repository.ProdutoRepository;
import br.com.AdaCommerce.service.interfacy.OpenFoodFactsService;
import br.com.AdaCommerce.service.interfacy.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "produtos")
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final OpenFoodFactsService openFoodFactsService;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository,
                              ProdutoMapper produtoMapper,
                              OpenFoodFactsService openFoodFactsService) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
        this.openFoodFactsService = openFoodFactsService;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public ProdutoResponse criarProduto(ProdutoRequest request) {
        Produto produto = produtoMapper.toEntity(request);
        produto = produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    @Override
    @Cacheable(key = "#id")
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        return produtoMapper.toResponse(produto);
    }

    @Override
    @Cacheable(key = "'page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    public Page<ProdutoResponse> listarProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(produtoMapper::toResponse);
    }

    @Override
    @Cacheable(key = "'nome_' + #nome + '_page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    public Page<ProdutoResponse> buscarPorNome(String nome, Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return produtos.map(produtoMapper::toResponse);
    }

    @Override
    @Cacheable(key = "'categoria_' + #categoria + '_page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    public Page<ProdutoResponse> buscarPorCategoria(String categoria, Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findByCategoriaContainingIgnoreCase(categoria, pageable);
        return produtos.map(produtoMapper::toResponse);
    }

    @Override
    @Cacheable(key = "'termo_' + #termo + '_page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    public Page<ProdutoResponse> buscarPorNomeOuCategoria(String termo, Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findByNomeOrCategoria(termo, pageable);
        return produtos.map(produtoMapper::toResponse);
    }

    @Override
    @Cacheable(key = "'nome_' + #nome + '_categoria_' + #categoria + '_page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    public Page<ProdutoResponse> buscarPorNomeECategoria(String nome, String categoria, Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findByNomeAndCategoria(nome, categoria, pageable);
        return produtos.map(produtoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setMarca(request.getMarca());
        produto.setCategoria(request.getCategoria());

        produto = produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public ProdutoResponse importarProdutoPorCodigoBarras(String codigoBarras) {
        Produto produto = openFoodFactsService.buscarProdutoPorCodigoBarras(codigoBarras);
        produto = produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }
}

