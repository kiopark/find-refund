package szs.findrefund.config;

import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket swaggerApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(swaggerInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("szs.findrefund.web"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()));
  }

  private ApiInfo swaggerInfo() {
    return new ApiInfoBuilder()
        .title("삼쩜삼 회원의 숨은 환급금 찾기 서비스")
        .description("SZS Find-Refund-Project")
        .version("1")
        .contact(new Contact("Park Ki O", "", "goril2504@gmail.com"))
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header");
  }

  private SecurityContext securityContext() {
    return springfox
        .documentation
        .spi.service
        .contexts
        .SecurityContext
        .builder()
        .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }

}
