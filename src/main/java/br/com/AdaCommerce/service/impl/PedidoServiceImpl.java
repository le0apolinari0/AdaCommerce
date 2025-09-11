package br.com.AdaCommerce.service.impl;

import br.com.AdaCommerce.dto.request.PedidoRequest;
import br.com.AdaCommerce.dto.response.PedidoResponse;
import br.com.AdaCommerce.execption.BusinessException;
import br.com.AdaCommerce.mapper.PedidoMapper;
import br.com.AdaCommerce.model.*;
import br.com.AdaCommerce.repository.PedidoRepository;
import br.com.AdaCommerce.service.interfacy.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        Pedido pedido = pedidoMapper.toEntity(request);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse adicionarItem(Long pedidoId, Long produtoId, Integer quantidade, BigDecimal precoVenda) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.ABERTO)) {
            throw new BusinessException("Não é possível adicionar itens a um pedido fechado");
        }

        ItemPedido item = new ItemPedido();
        Produto produto = new Produto();
        produto.setId(produtoId);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoVenda(precoVenda);

        pedido.adicionarItem(item);
        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse removerItem(Long pedidoId, Long itemId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.ABERTO)) {
            throw new BusinessException("Não é possível remover itens de um pedido fechado");
        }

        ItemPedido item = pedido.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Item não encontrado no pedido"));

        pedido.removerItem(item);
        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse atualizarQuantidadeItem(Long pedidoId, Long itemId, Integer quantidade) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.ABERTO)) {
            throw new BusinessException("Não é possível alterar itens de um pedido fechado");
        }

        ItemPedido item = pedido.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Item não encontrado no pedido"));

        item.setQuantidade(quantidade);
        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse finalizarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.ABERTO)) {
            throw new BusinessException("Pedido não está aberto para finalização");
        }

        if (pedido.getItens().isEmpty()) {
            throw new BusinessException("Pedido deve ter pelo menos um item");
        }

        if (pedido.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor do pedido deve ser maior que zero");
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);

        // Notificar cliente via e-mail (implementação simplificada)
        notificarCliente(pedido.getCliente(), "Pedido aguardando pagamento");

        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse processarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.AGUARDANDO_PAGAMENTO) ||
                !pedido.getStatusPagamento().equals(StatusPagamento.AGUARDANDO_PAGAMENTO)) {
            throw new BusinessException("Pedido não está aguardando pagamento");
        }

        pedido.setStatusPagamento(StatusPagamento.PAGO);

        // Notificar cliente via e-mail (implementação simplificada)
        notificarCliente(pedido.getCliente(), "Pagamento confirmado");

        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse finalizarEntrega(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!pedido.getStatusPagamento().equals(StatusPagamento.PAGO)) {
            throw new BusinessException("Pedido não foi pago");
        }

        pedido.setStatus(StatusPedido.FINALIZADO);

        // Notificar cliente via e-mail (implementação simplificada)
        notificarCliente(pedido.getCliente(), "Pedido entregue");

        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(pedido);
    }

    private void notificarCliente(Cliente cliente, String mensagem) {
        // Implementação simplificada de notificação
        System.out.println("Notificando cliente " + cliente.getEmail() + ": " + mensagem);
    }
}

