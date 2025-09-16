package br.com.AdaCommerce.controller;

import br.com.AdaCommerce.dto.request.PedidoRequest;
import br.com.AdaCommerce.dto.response.ApiResponse;
import br.com.AdaCommerce.dto.response.PedidoResponse;
import br.com.AdaCommerce.service.interfacy.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações para gestão completa de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> criarPedido(
            @Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.criarPedido(request);
        return ResponseEntity.ok(ApiResponse.success("Pedido criado com sucesso", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    public ResponseEntity<ApiResponse<PedidoResponse>> buscarPorId(@PathVariable Long id) {
        PedidoResponse response = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{pedidoId}/itens")
    @Operation(summary = "Adicionar item a um pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> adicionarItem(
            @PathVariable Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam BigDecimal precoVenda) {
        PedidoResponse response = pedidoService.adicionarItem(pedidoId, produtoId, quantidade, precoVenda);
        return ResponseEntity.ok(ApiResponse.success("Item adicionado com sucesso", response));
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    @Operation(summary = "Remover item de um pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId) {
        PedidoResponse response = pedidoService.removerItem(pedidoId, itemId);
        return ResponseEntity.ok(ApiResponse.success("Item removido com sucesso", response));
    }

    @PutMapping("/{pedidoId}/itens/{itemId}")
    @Operation(summary = "Atualizar quantidade de item em um pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> atualizarQuantidadeItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId,
            @RequestParam Integer quantidade) {
        PedidoResponse response = pedidoService.atualizarQuantidadeItem(pedidoId, itemId, quantidade);
        return ResponseEntity.ok(ApiResponse.success("Quantidade atualizada com sucesso", response));
    }

    @PostMapping("/{pedidoId}/finalizar")
    @Operation(summary = "Finalizar um pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> finalizarPedido(@PathVariable Long pedidoId) {
        PedidoResponse response = pedidoService.finalizarPedido(pedidoId);
        return ResponseEntity.ok(ApiResponse.success("Pedido finalizado com sucesso", response));
    }

    @PostMapping("/{pedidoId}/pagar")
    @Operation(summary = "Processar pagamento de um pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> processarPagamento(@PathVariable Long pedidoId) {
        PedidoResponse response = pedidoService.processarPagamento(pedidoId);
        return ResponseEntity.ok(ApiResponse.success("Pagamento processado com sucesso", response));
    }

    @PostMapping("/{pedidoId}/entregar")
    @Operation(summary = "Finalizar entrega de um pedido")
    public ResponseEntity<ApiResponse<PedidoResponse>> finalizarEntrega(@PathVariable Long pedidoId) {
        PedidoResponse response = pedidoService.finalizarEntrega(pedidoId);
        return ResponseEntity.ok(ApiResponse.success("Entrega finalizada com sucesso", response));
    }
}