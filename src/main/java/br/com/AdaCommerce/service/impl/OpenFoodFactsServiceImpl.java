package br.com.AdaCommerce.service.impl;

import br.com.AdaCommerce.model.Produto;
import br.com.AdaCommerce.service.interfacy.OpenFoodFactsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
@Service
public class OpenFoodFactsServiceImpl implements OpenFoodFactsService {

    private final WebClient webClient;

    @Autowired
    public OpenFoodFactsServiceImpl(@Qualifier("openFoodFactsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Produto buscarProdutoPorCodigoBarras(String codigoBarras) {
        return webClient.get()
                .uri("/product/{code}.json", codigoBarras)
                .retrieve()
                .bodyToMono(OpenFoodFactsResponse.class)
                .map(this::mapToProduto)
                .block();
    }

    private Produto mapToProduto(OpenFoodFactsResponse response) {
        if (response == null || response.getProduct() == null) {
            throw new RuntimeException("Produto não encontrado na API Open Food Facts");
        }

        OpenFoodFactsResponse.Product product = response.getProduct();
        Produto produto = new Produto();

        produto.setNome(product.getProductName());
        produto.setDescricao(product.getGenericName());
        produto.setCodigoBarras(product.getCode());
        produto.setMarca(product.getBrands());
        produto.setCategoria(product.getCategories());

        // Mapear nutrientes para preço (exemplo simplificado)
        if (product.getNutriments() != null) {
            // Usar um valor padrão ou calcular com base nos nutrientes
            produto.setPreco(BigDecimal.valueOf(10.0));
        } else {
            produto.setPreco(BigDecimal.valueOf(5.0));
        }

        return produto;
    }

    // Classes internas para mapeamento da resposta da API
    private static class OpenFoodFactsResponse {
        private Product product;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        private static class Product {
            private String code;
            private String productName;
            private String genericName;
            private String brands;
            private String categories;
            private Nutriments nutriments;

            public String getCode() { return code; }
            public void setCode(String code) { this.code = code; }

            public String getProductName() { return productName; }
            public void setProductName(String productName) { this.productName = productName; }

            public String getGenericName() { return genericName; }
            public void setGenericName(String genericName) { this.genericName = genericName; }

            public String getBrands() { return brands; }
            public void setBrands(String brands) { this.brands = brands; }

            public String getCategories() { return categories; }
            public void setCategories(String categories) { this.categories = categories; }

            public Nutriments getNutriments() { return nutriments; }
            public void setNutriments(Nutriments nutriments) { this.nutriments = nutriments; }
        }

        private static class Nutriments {
            private Double energyKj;
            private Double energyKcal;
            private Double fat;
            private Double saturatedFat;
            private Double carbohydrates;
            private Double sugars;
            private Double proteins;
            private Double salt;

        }
    }
}
