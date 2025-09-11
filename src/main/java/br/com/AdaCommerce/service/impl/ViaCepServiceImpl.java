package br.com.AdaCommerce.service.impl;

import br.com.AdaCommerce.model.Endereco;
import br.com.AdaCommerce.service.interfacy.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ViaCepServiceImpl implements ViaCepService {

    private final WebClient webClient;

    @Autowired
    public ViaCepServiceImpl(@Qualifier("viaCepWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Endereco buscarEnderecoPorCep(String cep) {
        return webClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(ViaCepResponse.class)
                .map(this::mapToEndereco)
                .block();
    }

    private Endereco mapToEndereco(ViaCepResponse response) {
        if (response == null || response.getCep() == null) {
            throw new RuntimeException("CEP não encontrado");
        }

        Endereco endereco = new Endereco();
        endereco.setCep(response.getCep().replace("-", ""));
        endereco.setLogradouro(response.getLogradouro());
        endereco.setBairro(response.getBairro());
        endereco.setCidade(response.getLocalidade());
        endereco.setEstado(response.getUf());

        return endereco;
    }

    private static class ViaCepResponse {
        private String cep;
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;

        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }

        public String getLogradouro() { return logradouro; }
        public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

        public String getComplemento() { return complemento; }
        public void setComplemento(String complemento) { this.complemento = complemento; }

        public String getBairro() { return bairro; }
        public void setBairro(String bairro) { this.bairro = bairro; }

        public String getLocalidade() { return localidade; }
        public void setLocalidade(String localidade) { this.localidade = localidade; }

        public String getUf() { return uf; }
        public void setUf(String uf) { this.uf = uf; }
    }
}

