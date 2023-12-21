package com.firstapi.config;


import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
//@OpenAPIDefinition(info = @Info(title = "BlogApplication", version = "1.0"))
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class SwaggerConfig {
  /*  public static final String Authorization_Header = "Authorization";
    private ApiKey apiKey(){
        return new ApiKey("JWT",Authorization_Header,"header");
    }

    private List<SecurityContext> securityContexts(){
        return List.of(SecurityContext.builder().securityReferences(securityReferences()).build());
    }
    private List<SecurityReference> securityReferences(){
        AuthorizationScope scopes = new AuthorizationScope("global","access everything");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[] {scopes}));
    }
@Bean
public Docket api(){


    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(getInfo())
            .securityContexts(securityContexts())
            .securitySchemes(Arrays.asList(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();

}

    private ApiInfo getInfo() {
    return new ApiInfo("Blogging Application","This is demo for learning","1.0","Terms of service",new Contact("Jitendra","https://learncode.com","Jitendra@gmail.com"),"License","Api license url", Collections.emptyList());
    }*/
}
