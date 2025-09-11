package br.com.AdaCommerce.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteResponse {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoResponse endereco;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}

