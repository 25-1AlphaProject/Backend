package alpha.chupchup.common;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("척척밥사")
                .version("v0.0.1")
                .description("2025-1 알파 프로젝트");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
