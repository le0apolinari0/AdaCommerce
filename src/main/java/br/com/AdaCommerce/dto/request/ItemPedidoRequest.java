package br.com.AdaCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoRequest {
    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;

    @NotNull(message = "Preço de venda é obrigatório")
    private BigDecimal precoVenda;
}

