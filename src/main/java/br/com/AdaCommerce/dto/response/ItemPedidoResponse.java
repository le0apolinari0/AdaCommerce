package br.com.AdaCommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemPedidoResponse {
    private Long id;
    private ProdutoResponse produto;
    private Integer quantidade;
    private BigDecimal precoVenda;
    private BigDecimal subtotal;
    private LocalDateTime dataCriacao;
}

