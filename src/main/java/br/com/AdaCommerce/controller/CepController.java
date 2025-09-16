package br.com.AdaCommerce.controller;

import br.com.AdaCommerce.dto.response.ApiResponse;
import br.com.AdaCommerce.dto.response.EnderecoResponse;
import br.com.AdaCommerce.mapper.EnderecoMapper;
import br.com.AdaCommerce.model.Endereco;
import br.com.AdaCommerce.service.interfacy.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/cep")
@Tag(name = "CEP", description = "Consulta de endereços via API ViaCEP")
public class CepController {

    private final ViaCepService viaCepService;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    public CepController(ViaCepService viaCepService, EnderecoMapper enderecoMapper) {
        this.viaCepService = viaCepService;
        this.enderecoMapper = enderecoMapper;
    }

    @GetMapping("/{cep}")
    @Operation(
            summary = "Consultar endereço por CEP",
            description = "Retorna as informações do endereço correspondente ao CEP informado"
    )
    public ResponseEntity<ApiResponse<EnderecoResponse>> buscarPorCep(@PathVariable String cep) {
        try {
            Endereco endereco = viaCepService.buscarEnderecoPorCep(cep);
            EnderecoResponse response = enderecoMapper.toResponse(endereco);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("CEP não encontrado ou inválido"));
        }
    }
}