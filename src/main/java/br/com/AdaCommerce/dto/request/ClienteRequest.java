package br.com.AdaCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteRequest {
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    private String telefone;
    @NotNull(message = "Email é obrigatório")
    private String email;
    private EnderecoRequest endereco;
}

