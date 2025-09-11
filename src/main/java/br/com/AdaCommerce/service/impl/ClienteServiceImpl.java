package br.com.AdaCommerce.service.impl;

import br.com.AdaCommerce.dto.request.ClienteRequest;
import br.com.AdaCommerce.dto.request.EnderecoRequest;
import br.com.AdaCommerce.dto.response.ClienteResponse;
import br.com.AdaCommerce.execption.BusinessException;
import br.com.AdaCommerce.mapper.ClienteMapper;
import br.com.AdaCommerce.mapper.EnderecoMapper;
import br.com.AdaCommerce.model.Cliente;
import br.com.AdaCommerce.model.Endereco;
import br.com.AdaCommerce.repository.ClienteRepository;
import br.com.AdaCommerce.service.interfacy.ClienteService;
import br.com.AdaCommerce.service.interfacy.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "clientes")
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final EnderecoMapper enderecoMapper;
    private final ViaCepService viaCepService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              ClienteMapper clienteMapper,
                              EnderecoMapper enderecoMapper,
                              ViaCepService viaCepService) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.enderecoMapper = enderecoMapper;
        this.viaCepService = viaCepService;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public ClienteResponse criarCliente(ClienteRequest request) {
        if (clienteRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("Já existe um cliente com este CPF");
        }

        Cliente cliente = clienteMapper.toEntity(request);

        // Processar endereço
        if (request.getEndereco() != null) {
            Endereco endereco = processarEndereco(request.getEndereco());
            cliente.setEndereco(endereco);
        }

        cliente = clienteRepository.save(cliente);
        return clienteMapper.toResponse(cliente);
    }

    @Override
    @Cacheable(key = "#id")
    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));
        return clienteMapper.toResponse(cliente);
    }

    @Override
    @Cacheable(key = "'page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    public Page<ClienteResponse> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(clienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public ClienteResponse atualizarCliente(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));

        if (!cliente.getCpf().equals(request.getCpf()) &&
                clienteRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("Já existe um cliente com este CPF");
        }

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setTelefone(request.getTelefone());
        cliente.setEmail(request.getEmail());

        // Processar endereço
        if (request.getEndereco() != null) {
            Endereco endereco = processarEndereco(request.getEndereco());
            cliente.setEndereco(endereco);
        }

        cliente = clienteRepository.save(cliente);
        return clienteMapper.toResponse(cliente);
    }

    private Endereco processarEndereco(EnderecoRequest enderecoRequest) {
        Endereco endereco = enderecoMapper.toEntity(enderecoRequest);

        // Se autoCompletar for true, buscar dados do CEP
        if (enderecoRequest.getAutoCompletar() != null && enderecoRequest.getAutoCompletar()) {
            try {
                Endereco enderecoCompleto = viaCepService.buscarEnderecoPorCep(enderecoRequest.getCep());

                // Manter o número e complemento fornecidos pelo usuário
                endereco.setLogradouro(enderecoCompleto.getLogradouro());
                endereco.setBairro(enderecoCompleto.getBairro());
                endereco.setCidade(enderecoCompleto.getCidade());
                endereco.setEstado(enderecoCompleto.getEstado());

                // Se o usuário não forneceu número, manter o existente
                if (enderecoRequest.getNumero() != null) {
                    endereco.setNumero(enderecoRequest.getNumero());
                }

                // Se o usuário não forneceu complemento, manter o existente
                if (enderecoRequest.getComplemento() != null) {
                    endereco.setComplemento(enderecoRequest.getComplemento());
                }
            } catch (Exception e) {
                // Logar erro mas não falhar a operação
                System.err.println("Erro ao buscar CEP: " + e.getMessage());
            }
        }

        return endereco;
    }
}

