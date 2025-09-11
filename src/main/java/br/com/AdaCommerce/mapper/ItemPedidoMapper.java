package br.com.AdaCommerce.mapper;

import br.com.AdaCommerce.dto.request.ItemPedidoRequest;
import br.com.AdaCommerce.dto.response.ItemPedidoResponse;
import br.com.AdaCommerce.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {
    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(source = "produtoId", target = "produto.id")
    ItemPedido toEntity(ItemPedidoRequest request);

    ItemPedidoResponse toResponse(ItemPedido itemPedido);
}

