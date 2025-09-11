package br.com.AdaCommerce.service.interfacy;

import br.com.AdaCommerce.model.Produto;

public interface OpenFoodFactsService {
    Produto buscarProdutoPorCodigoBarras(String codigoBarras);
}

