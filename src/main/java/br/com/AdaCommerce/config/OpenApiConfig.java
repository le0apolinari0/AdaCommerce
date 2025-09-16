package br.com.AdaCommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@SecurityScheme(
//        name = "bearerAuth",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("contato@adacommerce.com.br");
        contact.setName("Suporte AdaCommerce");
        contact.setUrl("https://www.adacommerce.com.br");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("API AdaCommerce")
                .version("1.0.0")
                .contact(contact)
                .description("API completa para gerenciamento de e-commerce da AdaTech. " +
                        "Inclui gestão de clientes, produtos, pedidos e integração com APIs externas.")
                .license(mitLicense);

        return new OpenAPI().info(info);
    }
}
