package br.com.AdaCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoRequest {
    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;
}

