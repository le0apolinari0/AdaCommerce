package br.com.AdaCommerce.service.interfacy;

import br.com.AdaCommerce.dto.request.PedidoRequest;
import br.com.AdaCommerce.dto.response.PedidoResponse;

import java.math.BigDecimal;

public interface PedidoService {
    PedidoResponse criarPedido(PedidoRequest request);
    PedidoResponse buscarPorId(Long id);
    PedidoResponse adicionarItem(Long pedidoId, Long produtoId, Integer quantidade, BigDecimal precoVenda);
    PedidoResponse removerItem(Long pedidoId, Long itemId);
    PedidoResponse atualizarQuantidadeItem(Long pedidoId, Long itemId, Integer quantidade);
    PedidoResponse finalizarPedido(Long pedidoId);
    PedidoResponse processarPagamento(Long pedidoId);
    PedidoResponse finalizarEntrega(Long pedidoId);

}

