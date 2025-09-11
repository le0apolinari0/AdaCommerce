package br.com.AdaCommerce.mapper;

import br.com.AdaCommerce.dto.request.EnderecoRequest;
import br.com.AdaCommerce.dto.response.EnderecoResponse;
import br.com.AdaCommerce.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Endereco toEntity(EnderecoRequest request);

    EnderecoResponse toResponse(Endereco endereco);
}
