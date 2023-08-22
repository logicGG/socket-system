package club.ovelya.socketsystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Socket System Apis")
                        .description("Socket System Apis doc")
                        .version("1.0")
                        .license(new License()
                                .name("Apache2.0")
                                .url("https://springdoc.org/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url("https://logicgg.github.io/"));
    }
}
