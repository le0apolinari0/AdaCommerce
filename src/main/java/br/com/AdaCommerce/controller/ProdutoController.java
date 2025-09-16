package br.com.AdaCommerce.controller;

import br.com.AdaCommerce.dto.request.ProdutoRequest;
import br.com.AdaCommerce.dto.response.ApiResponse;
import br.com.AdaCommerce.dto.response.ProdutoResponse;
import br.com.AdaCommerce.service.interfacy.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações para gestão de produtos do catálogo")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto")
    public ResponseEntity<ApiResponse<ProdutoResponse>> criarProduto(
            @Valid @RequestBody ProdutoRequest request) {
        ProdutoResponse response = produtoService.criarProduto(request);
        return ResponseEntity.ok(ApiResponse.success("Produto criado com sucesso", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ApiResponse<ProdutoResponse>> buscarPorId(@PathVariable Long id) {
        ProdutoResponse response = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos com paginação")
    public ResponseEntity<ApiResponse<Page<ProdutoResponse>>> listarProdutos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ProdutoResponse> response = produtoService.listarProdutos(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search/nome")
    @Operation(summary = "Buscar produtos por nome")
    public ResponseEntity<ApiResponse<Page<ProdutoResponse>>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ProdutoResponse> response = produtoService.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search/categoria")
    @Operation(summary = "Buscar produtos por categoria")
    public ResponseEntity<ApiResponse<Page<ProdutoResponse>>> buscarPorCategoria(
            @RequestParam String categoria,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ProdutoResponse> response = produtoService.buscarPorCategoria(categoria, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos por nome ou categoria")
    public ResponseEntity<ApiResponse<Page<ProdutoResponse>>> buscarPorNomeOuCategoria(
            @RequestParam String termo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ProdutoResponse> response = produtoService.buscarPorNomeOuCategoria(termo, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search/diverso")
    @Operation(summary = "Buscar produtos por nome e/ou categoria")
    public ResponseEntity<ApiResponse<Page<ProdutoResponse>>> buscarPorNomeECategoria(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ProdutoResponse> response = produtoService.buscarPorNomeECategoria(nome, categoria, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um produto")
    public ResponseEntity<ApiResponse<ProdutoResponse>> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequest request) {
        ProdutoResponse response = produtoService.atualizarProduto(id, request);
        return ResponseEntity.ok(ApiResponse.success("Produto atualizado com sucesso", response));
    }

    @PostMapping("/importar/{codigoBarras}")
    @Operation(summary = "Importar produto por código de barras")
    public ResponseEntity<ApiResponse<ProdutoResponse>> importarProduto(
            @PathVariable String codigoBarras) {
        ProdutoResponse response = produtoService.importarProdutoPorCodigoBarras(codigoBarras);
        return ResponseEntity.ok(ApiResponse.success("Produto importado com sucesso", response));
    }
}
