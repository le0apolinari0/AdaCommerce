package br.com.AdaCommerce.service.interfacy;

import br.com.AdaCommerce.dto.request.ClienteRequest;
import br.com.AdaCommerce.dto.response.ClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    ClienteResponse criarCliente(ClienteRequest request);
    ClienteResponse buscarPorId(Long id);
    Page<ClienteResponse> listarClientes(Pageable pageable);
    ClienteResponse atualizarCliente(Long id, ClienteRequest request);
}
