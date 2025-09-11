package br.com.AdaCommerce.mapper;

import br.com.AdaCommerce.dto.request.ProdutoRequest;
import br.com.AdaCommerce.dto.response.ProdutoResponse;
import br.com.AdaCommerce.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Produto toEntity(ProdutoRequest request);

    ProdutoResponse toResponse(Produto produto);
}

