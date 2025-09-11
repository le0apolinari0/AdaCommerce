package br.com.AdaCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequest {
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    private BigDecimal preco;

    private String codigoBarras;
    private String marca;
    private String categoria;
}

