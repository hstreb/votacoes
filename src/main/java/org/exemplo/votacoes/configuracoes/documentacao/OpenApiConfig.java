package org.exemplo.votacoes.configuracoes.documentacao;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${openapi.titulo}")
    private String titulo;

    @Value("${openapi.descricao}")
    private String descricao;

    @Value("${openapi.versao}")
    private String versao;

    @Bean
    public OpenAPI customOpenAPI2() {
        return new OpenAPI()
                .info(new Info()
                        .title(titulo)
                        .description(descricao)
                        .version(versao));
    }

}
