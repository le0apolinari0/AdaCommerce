package br.com.AdaCommerce.mapper;

import br.com.AdaCommerce.dto.request.ClienteRequest;
import br.com.AdaCommerce.dto.response.ClienteResponse;
import br.com.AdaCommerce.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Cliente toEntity(ClienteRequest request);

    ClienteResponse toResponse(Cliente cliente);
}

