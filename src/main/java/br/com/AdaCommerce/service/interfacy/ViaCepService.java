package br.com.AdaCommerce.service.interfacy;

import br.com.AdaCommerce.model.Endereco;

public interface ViaCepService {
    Endereco buscarEnderecoPorCep(String cep);
}

