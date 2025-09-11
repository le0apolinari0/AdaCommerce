package br.com.AdaCommerce.controller;

import br.com.AdaCommerce.dto.request.ClienteRequest;
import br.com.AdaCommerce.dto.response.ApiResponse;
import br.com.AdaCommerce.dto.response.ClienteResponse;
import br.com.AdaCommerce.service.interfacy.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> criarCliente(
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.criarCliente(request);
        return ResponseEntity.ok(ApiResponse.success("Cliente criado com sucesso", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> buscarPorId(@PathVariable Long id) {
        ClienteResponse response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClienteResponse>>> listarClientes(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ClienteResponse> response = clienteService.listarClientes(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> atualizarCliente(
            @PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.atualizarCliente(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cliente atualizado com sucesso", response));
    }
}

