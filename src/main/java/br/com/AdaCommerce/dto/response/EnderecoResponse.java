package br.com.AdaCommerce.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnderecoResponse {
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}

