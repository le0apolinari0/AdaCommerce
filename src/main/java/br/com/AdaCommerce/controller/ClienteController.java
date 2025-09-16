package br.com.AdaCommerce.controller;

import br.com.AdaCommerce.dto.request.ClienteRequest;
import br.com.AdaCommerce.dto.response.ApiResponse;
import br.com.AdaCommerce.dto.response.ClienteResponse;
import br.com.AdaCommerce.service.interfacy.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operações para gestão de clientes do e-commerce")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente")
    public ResponseEntity<ApiResponse<ClienteResponse>> criarCliente(
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.criarCliente(request);
        return ResponseEntity.ok(ApiResponse.success("Cliente criado com sucesso", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<ApiResponse<ClienteResponse>> buscarPorId(@PathVariable Long id) {
        ClienteResponse response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes com paginação")
    public ResponseEntity<ApiResponse<Page<ClienteResponse>>> listarClientes(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ClienteResponse> response = clienteService.listarClientes(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um cliente por ID")
    public ResponseEntity<ApiResponse<ClienteResponse>> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.atualizarCliente(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cliente atualizado com sucesso", response));
    }
}