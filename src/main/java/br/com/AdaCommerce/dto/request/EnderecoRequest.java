package br.com.AdaCommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnderecoRequest {
    @NotNull(message = "CEP é obrigatório")
    private String cep;
    private String logradouro;
    @NotNull(message = "Numero é obrigatório")
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    private Boolean autoCompletar = true;
}

