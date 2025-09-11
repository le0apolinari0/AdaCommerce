package br.com.AdaCommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProdutoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String codigoBarras;
    private String marca;
    private String categoria;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
