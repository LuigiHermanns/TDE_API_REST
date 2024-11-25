package restaurant_recipes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API TDE RESTAAAAAAAAAAAAAAAAA")
                .description("DOCUMENTAÇÃO DA API REST DO TDE")
                .version("1.0"))
            .addServersItem(new Server()
                .url("https://tde-api-rest-luigi-793818627834.us-central1.run.app")
                .description("Generated server url"));
    }
}
