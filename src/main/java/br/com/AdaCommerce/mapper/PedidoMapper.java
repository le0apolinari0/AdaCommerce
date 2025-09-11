package br.com.AdaCommerce.mapper;

import br.com.AdaCommerce.dto.request.PedidoRequest;
import br.com.AdaCommerce.dto.response.PedidoResponse;
import br.com.AdaCommerce.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemPedidoMapper.class})
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "statusPagamento", ignore = true)
    @Mapping(target = "itens", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(source = "clienteId", target = "cliente.id")
    Pedido toEntity(PedidoRequest request);

    PedidoResponse toResponse(Pedido pedido);
}

