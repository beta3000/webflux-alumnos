package rodriguez.ciro.webfluxalumnos.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @io.swagger.v3.oas.annotations.info.Info(
            title = "WebFlux Alumnos API",
            description = "API REST para gestión de alumnos utilizando Spring WebFlux",
            version = "1.0.0",
            contact = @Contact(name = "Ciro Rodriguez", email = "ciro@example.com"),
            license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")),
    servers = {@Server(description = "Development Server", url = "http://localhost:8080")})
public class OpenApiConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new io.swagger.v3.oas.models.info.Info()
                .title("WebFlux Alumnos API")
                .description("API REST para gestión de alumnos utilizando Spring WebFlux")
                .version("1.0.0"));
  }
}
