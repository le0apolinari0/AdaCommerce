package br.com.AdaCommerce.dto.response;

import br.com.AdaCommerce.model.StatusPagamento;
import br.com.AdaCommerce.model.StatusPedido;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponse {
    private Long id;
    private ClienteResponse cliente;
    private LocalDateTime dataCriacao;
    private StatusPedido status;
    private StatusPagamento statusPagamento;
    private List<ItemPedidoResponse> itens;
    private BigDecimal total;
    private LocalDateTime dataAtualizacao;
}

